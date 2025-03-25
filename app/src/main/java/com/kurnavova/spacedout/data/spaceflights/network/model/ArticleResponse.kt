package com.kurnavova.spacedout.data.spaceflights.network.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("authors")
    val authors: List<Author>,

    @SerializedName("published_at")
    val publishedAt: String
)

data class Author(
    @SerializedName("name")
    val name: String
)
