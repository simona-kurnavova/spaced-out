package com.kurnavova.spacedout.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.favourites.ui.FavouriteArticlesScreenRoot
import com.kurnavova.spacedout.features.newsdetail.ui.NewsDetailScreenRoot
import com.kurnavova.spacedout.features.newslist.ui.NewsListScreenRoot
import com.kurnavova.spacedout.navigation.route.Favourites
import com.kurnavova.spacedout.navigation.route.NewsDetail
import com.kurnavova.spacedout.navigation.route.NewsList

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    var toolbarState: ToolbarState by remember {
        mutableStateOf(ToolbarState(R.string.app_name, false, false))
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Toolbar(
                toolbarState = toolbarState,
                goToFavourites = { navController.navigate(Favourites) },
                popBackStack = navController::popBackStack
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NewsList,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<NewsList> {
                LaunchedEffect(Unit) {
                    toolbarState = ToolbarState(
                        title = R.string.app_name,
                        backActionAvailable = false,
                        showFavourites = true
                    )
                }

                NewsListScreenRoot { id: Int ->
                    navController.navigate(NewsDetail(id))
                }
            }
            composable<NewsDetail> { backStackEntry ->
                LaunchedEffect(Unit) {
                    toolbarState = ToolbarState(
                        title = R.string.news_detail_toolbar_title,
                        backActionAvailable = true,
                        showFavourites = false
                    )
                }

                val route = backStackEntry.toRoute<NewsDetail>()
                NewsDetailScreenRoot(id = route.id)
            }
            composable<Favourites> {
                LaunchedEffect(Unit) {
                    toolbarState = ToolbarState(
                        title = R.string.favourites_toolbar_title,
                        backActionAvailable = true,
                        showFavourites = false
                    )
                }

                FavouriteArticlesScreenRoot(
                    navigateToDetail = {  navController.navigate(NewsDetail(it)) }
                )
            }
        }
    }
}
