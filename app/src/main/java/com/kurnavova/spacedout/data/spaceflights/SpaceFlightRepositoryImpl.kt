package com.kurnavova.spacedout.data.spaceflights

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDao
import com.kurnavova.spacedout.data.spaceflights.local.mapper.toDomain
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import com.kurnavova.spacedout.data.spaceflights.network.SpaceFlightApi
import com.kurnavova.spacedout.data.spaceflights.network.mapper.toApiResult
import com.kurnavova.spacedout.data.spaceflights.network.mapper.toDomain
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ArticleDetail
import com.kurnavova.spacedout.domain.model.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Implementation of the SpaceFlightRepository.
 *
 * @property spaceFlightApi The DAO for space flight data.
 */
class SpaceFlightRepositoryImpl(
    private val spaceFlightApi: SpaceFlightApi,
    private val articleDao: ArticleDao
) : SpaceFlightRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesWithPaging(): Flow<PagingData<ArticleDetail>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = ArticleRemoteMediator(spaceFlightApi, articleDao),
        pagingSourceFactory = { articleDao.getPagedArticles() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }

    override suspend fun getArticleById(id: Int): ApiResult<ArticleDetail> =
        queryArticleFromCache(id)
            ?.let { ApiResult.Success(it.toDomain()) }
            ?: runNetworkQuery(
                query = { spaceFlightApi.getArticle(id) },
                processBody = { it.toDomain() }
            )

    private suspend fun <T, R> runNetworkQuery(
        query: suspend () -> Response<T>,
        processBody: (T) -> R,
    ): ApiResult<R> =
        withContext(dispatcher) {
            val result = runCatching { query() }

            if (result.isFailure) {
                return@withContext ApiResult.Error(ErrorType.Network)
            } else {
                return@withContext result.getOrNull()
                    ?.toApiResult(processBody)
                    ?: ApiResult.Error(ErrorType.Unknown)
            }
        }

    private suspend fun queryArticleFromCache(id: Int): ArticleEntity? = withContext(dispatcher) {
        Log.d(TAG, "Attempting to retrieve article with id $id from cache")
        val result = runCatching { articleDao.getById(id) }

        Log.d(TAG, "Result of cache success = ${result.isSuccess}")
        result.getOrNull()
    }
}

private const val TAG = "SpaceFlightRepositoryImpl"
private const val PAGE_SIZE = 10
