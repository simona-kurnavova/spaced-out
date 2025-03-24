package com.kurnavova.spacedout.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleListResponse(
    @SerializedName("results")
    val results: List<ArticleListItem>,
)

data class ArticleListItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("summary")
    val summary: String,
)
