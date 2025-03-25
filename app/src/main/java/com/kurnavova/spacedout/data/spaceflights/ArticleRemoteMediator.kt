package com.kurnavova.spacedout.data.spaceflights

import android.util.Log
import androidx.core.net.toUri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDao
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import com.kurnavova.spacedout.data.spaceflights.mapper.toEntity
import com.kurnavova.spacedout.data.spaceflights.network.SpaceFlightApi
import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi

/**
 * Remote mediator for articles. This class is responsible for loading articles from the API and caching them in the database.
 *
 * @property api The SpaceFlightApi instance.
 * @property articleDao The ArticleDao instance.
 */
@OptIn(ExperimentalPagingApi::class, ExperimentalAtomicApi::class)
class ArticleRemoteMediator(
    private val api: SpaceFlightApi,
    private val articleDao: ArticleDao
) : RemoteMediator<Int, ArticleEntity>() {
    private var lastOffset: AtomicReference<Int> = AtomicReference(1)

    // Note: There should be some better error handling here - custom exceptions, etc.
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastOffset.load() + state.config.pageSize
            }

            val response = api.getPagedArticles(offset = offset, limit = state.config.pageSize)
            val result = response.body()

            if (!response.isSuccessful || result == null) {
                Log.d(TAG, "API Error: ${response.code()}")

                return MediatorResult.Error(Exception("API Error: ${response.code()}"))
            }

            // Cache articles
            cacheArticles(result.results)
            lastOffset.store(offset)

            val endOfPaginationReached = result.next.extractOffsetOrNull() == null

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun cacheArticles(articles: List<ArticleResponse>) {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Caching articles of size: ${articles.size}")
            articleDao.insertArticles(articles.map { it.toEntity() })
        }
    }

    private fun String?.extractOffsetOrNull(): Int? = this?.let {
        it.toUri().getQueryParameter(OFFSET_PARAM)?.toIntOrNull()
    }
}

private const val OFFSET_PARAM = "offset"

private const val TAG = "ArticleRemoteMediator"
