package com.kurnavova.spacedout.data.spaceflights.local

import androidx.paging.PagingSource
import androidx.room.*
import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity

/**
 * Data access object for articles.
 */
@Dao
internal interface ArticleDao {
    /**
     * Returns a [PagingSource] of articles.
     */
    @Query("SELECT * FROM articles ORDER BY date DESC")
    fun getPagedArticles(): PagingSource<Int, ArticleEntity>

    /**
     * Returns a [PagingSource] of favorite articles.
     */
    @Query("SELECT * FROM articles WHERE isFavourite = 1 ORDER BY date DESC")
    fun getPagedFavouriteArticles(): PagingSource<Int, ArticleEntity>

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
    @Query("DELETE FROM articles WHERE date < :thresholdDate AND isFavourite = 0")
    suspend fun deleteOlderThan(thresholdDate: String)

    /**
     * Updates the isFavourite flag of an article.
     *
     * @param id Unique identifier.
     * @param isFavourite New value of the isFavourite flag.
     */
    @Query("UPDATE articles SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun updateFavouriteStatus(id: Int, isFavourite: Boolean)

    /**
     * Inserts articles into the database. Will replace on conflict.
     *
     * @param articles List of articles.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    /**
     * Upserts articles to database, maintaining their isFavourite flag.
     *
     * @param articles List of articles.
     */
    @Transaction
    suspend fun upsertArticles(articles: List<ArticleEntity>) {
        // Update existing articles to retain their isFavourite flag
        articles.forEach { article ->
            updateArticle(article.id, article.title, article.summary, article.url, article.imageUrl, article.author, article.date)
        }

        // Insert new articles
        insertArticles(articles)
    }

    /**
     * Updates an article in the database except for isFavourite flag.
     */
    @Query("UPDATE articles SET title = :title, summary = :summary, url = :url, imageUrl = :imageUrl, author = :author, date = :date WHERE id = :id")
    suspend fun updateArticle(
        id: Int,
        title: String,
        summary: String,
        url: String,
        imageUrl: String,
        author: List<String>,
        date: String
    )
}
