package com.seno.gencidev.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.seno.home.presentation.detail.DetailRoot
import com.seno.home.presentation.home.HomeRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Screen.Home>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it / 2 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it / 2 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it })
            },
            popExitTransition = {
                fadeOut()
            }
        ) {
            HomeRoot(
                onClickMovie = {
                    navController.navigate(Screen.Detail(it)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<Screen.Detail>(
            enterTransition = {
                slideInHorizontally (initialOffsetX = { it }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            },
        ) {
            DetailRoot(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}