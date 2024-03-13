package com.example.myapplication.navigation

import Screens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.auth.screens.LandingPage
import com.example.myapplication.auth.screens.LoginScreen
import com.example.myapplication.presentation.chat.ChatScreen
import com.example.myapplication.presentation.settings.SettingsScreen

@Composable
fun BottomNavigation(
    modifier: Modifier,
    bottomBarNavController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = bottomBarNavController,
        startDestination = Screens.ScreensLandingPageRoute.route
    ) {
        composable(route = Screens.ScreensLandingPageRoute.route){
            LandingPage(navController = bottomBarNavController)
        }
        homeGraph(navController = bottomBarNavController)
        composable(route = Screens.ScreensLoginRoute.route){
            LoginScreen()
        }

        composable(route = Screens.ScreensChatRoute.route){
            ChatScreen()
        }
        composable(route = Screens.ScreensSettingsRoute.route){
            SettingsScreen()
        }
    }
}