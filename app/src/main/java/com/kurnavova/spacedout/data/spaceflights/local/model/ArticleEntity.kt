package com.kurnavova.spacedout.data.spaceflights.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an article in the database.
 *
 * @property id Unique identifier.
 * @property title Title of the article.
 * @property summary Summary of the article.
 * @property url URL of the article.
 * @property imageUrl URL of the image.
 * @property author List of authors.
 * @property date Date in ISO 8601 format.
 */
@Entity(tableName = TABLE_NAME, indices = [Index(value = ["date"])])
internal data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val summary: String,
    val url: String,
    val imageUrl: String,
    val author: List<String>,
    val date: String,
    val isFavourite: Boolean = false
)

private const val TABLE_NAME = "articles"
