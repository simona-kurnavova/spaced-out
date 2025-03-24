package com.kurnavova.spacedout.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleListResponse(
    @SerializedName("results")
    val results: List<ArticleResponse>,
)
