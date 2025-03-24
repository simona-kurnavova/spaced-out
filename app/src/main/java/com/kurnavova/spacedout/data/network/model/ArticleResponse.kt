package com.kurnavova.spacedout.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("summary")
    val summary: String
)
