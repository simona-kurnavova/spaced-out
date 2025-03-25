package com.kurnavova.spacedout.data.spaceflights.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "articles", indices = [Index(value = ["date"])])
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val summary: String,
    val url: String,
    val imageUrl: String,
    val author: String,
    val date: String
)
