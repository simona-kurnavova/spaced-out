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
import kotlin.concurrent.atomics.ExperimentalAtomicApi

/**
 * Remote mediator for articles. This class is responsible for loading articles from the API and caching them in the database.
 *
 * @property api The SpaceFlightApi instance.
 * @property articleDao The ArticleDao instance.
 */
@OptIn(ExperimentalPagingApi::class, ExperimentalAtomicApi::class)
internal class ArticleRemoteMediator(
    private val api: SpaceFlightApi,
    private val articleDao: ArticleDao
) : RemoteMediator<Int, ArticleEntity>() {

    // Note: There should be some better error handling here - custom exceptions that can be mapped in UI to localized strings, etc.
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0 // Refreshing, return 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> when {
                    state.pages.isEmpty() -> 0
                    else -> {
                        val lastPage = state.pages.last()
                        lastPage.data.size + (lastPage.prevKey ?: 0) + 1
                    }
                }
            }

            val (success, next) = loadAndCacheArticles(offset, state.config.pageSize)

            return if (!success) {
                MediatorResult.Error(Exception("API Error"))
            } else {
                MediatorResult.Success(endOfPaginationReached = next == null)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error: $e")
            MediatorResult.Error(e)
        }
    }

    /**
     * Loads articles from the API and caches them in the database.
     *
     * @return pair of success and next offset.
     */
    private suspend fun loadAndCacheArticles(offset: Int, pageSize: Int): Pair<Boolean, Int?> {
        val response = api.getPagedArticles(offset = offset, limit = pageSize)
        val result = response.body()

        if (!response.isSuccessful || result == null) {
            Log.d(TAG, "API Error: ${response.code()}")
            return false to null
        } else {
            // Cache articles
            cacheArticles(result.results)

            // Get next offset
            val next = result.next.extractOffsetOrNull()
            return true to next
        }
    }

    /**
     * Caches the articles in the database. It will replace in case article exists
     * (so that it mirrors any potential BE changes)
     *
     * @param articles The list of articles to cache.
     */
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
