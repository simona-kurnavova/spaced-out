package com.kurnavova.spacedout.data.spaceflights.network.model

import com.google.gson.annotations.SerializedName

data class ArticleListResponse(
    @SerializedName("previous")
    val previous: String?,

    @SerializedName("next")
    val next: String?,

    @SerializedName("results")
    val results: List<ArticleResponse>,
)
