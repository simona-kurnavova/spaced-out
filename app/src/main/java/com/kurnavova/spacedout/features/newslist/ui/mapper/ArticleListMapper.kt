package com.kurnavova.spacedout.features.newslist.ui.mapper

import com.kurnavova.spacedout.domain.model.ArticleItem
import com.kurnavova.spacedout.features.newslist.ui.Article
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

/**
 * Maps a list of [ArticleItem] to a list of [Article].
 */
fun List<ArticleItem>.toArticles(): ImmutableList<Article> = map {
    Article(
        id = it.id,
        title = it.title,
        summary = it.summary.trim(),
    )
}.toPersistentList()
