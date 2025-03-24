package com.kurnavova.spacedout.data.network

import com.kurnavova.spacedout.data.network.mapper.toApiResult
import com.kurnavova.spacedout.data.network.mapper.toDomain
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of the SpaceFlightRepository.
 *
 * @property spaceFlightDao The DAO for space flight data.
 */
class SpaceFlightRepositoryImpl(
    private val spaceFlightDao: SpaceFlightDao
) : SpaceFlightRepository {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getNews(): ApiResult<List<Article>> = withContext(dispatcher) {
        spaceFlightDao.getArticles().toApiResult { body ->
            body.toDomain()
        }
    }

    override suspend fun getArticleById(id: Int): ApiResult<Article> = withContext(dispatcher) {
        spaceFlightDao.getArticle(id).toApiResult { body ->
            body.toDomain()
        }
    }
}
