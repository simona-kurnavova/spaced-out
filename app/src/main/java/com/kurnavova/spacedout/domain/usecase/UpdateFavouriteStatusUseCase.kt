package com.kurnavova.spacedout.domain.usecase

import com.kurnavova.spacedout.domain.api.SpaceFlightRepository

/**
 * Use case for updating the favourite status of an article.
 */
class UpdateFavouriteStatusUseCase(
    private val repository: SpaceFlightRepository,
) {
    /**
     * Updates the favourite status of an article by its ID.
     *
     * @param id Unique identifier of the article.
     * @param isFavourite New favourite status (true or false).
     */
    suspend fun invoke(id: Int, isFavourite: Boolean): Unit =
        repository.updateFavouriteStatus(id, isFavourite)
}
