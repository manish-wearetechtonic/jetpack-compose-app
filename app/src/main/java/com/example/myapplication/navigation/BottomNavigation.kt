package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = Screens.ScreensHomeRoute.route
    ) {


        homeGraph(navController = bottomBarNavController)

        composable(route = Screens.ScreensChatRoute.route){
            ChatScreen()
        }
        composable(route = Screens.ScreensSettingsRoute.route){
            SettingsScreen()
        }
    }
}