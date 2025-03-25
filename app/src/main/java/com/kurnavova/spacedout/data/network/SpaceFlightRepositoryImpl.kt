package com.kurnavova.spacedout.data.network

import com.kurnavova.spacedout.data.network.mapper.toApiResult
import com.kurnavova.spacedout.data.network.mapper.toDomain
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ArticleDetail
import com.kurnavova.spacedout.domain.model.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Implementation of the SpaceFlightRepository.
 *
 * @property spaceFlightApi The DAO for space flight data.
 */
class SpaceFlightRepositoryImpl(
    private val spaceFlightApi: SpaceFlightApi
) : SpaceFlightRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    // TODO: This is asking for some paging.
    override suspend fun getNews(): ApiResult<List<ArticleDetail>> =
        safeQuery(
            query = { spaceFlightApi.getArticles() },
            processBody = { it.toDomain() }
        )

    override suspend fun getArticleById(id: Int): ApiResult<ArticleDetail> =
        safeQuery(
            query = { spaceFlightApi.getArticle(id) },
            processBody = { it.toDomain() }
        )

    private suspend fun <T, R> safeQuery(
        query: suspend () -> Response<T>,
        processBody: (T) -> R
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
}
