package com.kurnavova.spacedout.navigation.route

import kotlinx.serialization.Serializable

/**
 * Navigation routes.
 */
interface NavRoute

/**
 * News list route.
 */
@Serializable
data object NewsList : NavRoute

/**
 * News detail route.
 *
 * @param id News id.
 */
@Serializable
data class NewsDetail(val id: Int) : NavRoute
