package com.kurnavova.spacedout.data.spaceflights.local

import androidx.paging.PagingSource
import androidx.room.*
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity

/**
 * Data access object for articles.
 */
@Dao
interface ArticleDao {
    /**
     * Returns a [PagingSource] of articles.
     */
    @Query("SELECT * FROM articles ORDER BY date DESC")
    fun getPagedArticles(): PagingSource<Int, ArticleEntity>

    /**
     * Returns an article by its identifier.
     *
     * @param id Unique identifier.
     */
    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getById(id: Int): ArticleEntity?

    /**
     * Deletes articles older than the specified date.
     *
     * @param thresholdDate Date in ISO 8601 format.
     */
    @Query("DELETE FROM articles WHERE date < :thresholdDate")
    suspend fun deleteOlderThan(thresholdDate: String)

    /**
     * Inserts articles into the database. Will replace on conflict.
     *
     * @param articles List of articles.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
}
