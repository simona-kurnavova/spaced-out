package com.kurnavova.spacedout.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kurnavova.spacedout.features.newsdetail.ui.NewsDetailScreenRoot
import com.kurnavova.spacedout.features.newslist.ui.NewsListScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NewsList, modifier = modifier) {
        composable<NewsList> {
            NewsListScreenRoot { id: Int ->
                navController.navigate(NewsDetail(id))
            }
        }
        composable<NewsDetail> { backStackEntry ->
            val id = backStackEntry.id.toInt()
            NewsDetailScreenRoot(id = id)
        }
    }
}

@Serializable
object NewsList

@Serializable
data class NewsDetail(val id: Int)
