package com.kurnavova.spacedout.data.spaceflights.local

import androidx.paging.PagingSource
import androidx.room.*
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity

/**
 * Data access object for articles.
 */
@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY date DESC")
    fun getPagedArticles(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getById(id: Int): ArticleEntity?

    // TODO: this is wrong, fix it
    @Query("DELETE FROM articles WHERE date < :thresholdDate")
    suspend fun deleteOlderThan(thresholdDate: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
}
