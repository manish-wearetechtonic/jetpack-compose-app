package com.example.myapplication

import Screens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.screens.LandingPage
import com.example.myapplication.navigation.BottomNavigationItem
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                NavHost(navController, startDestination = Screens.ScreensLandingPageRoute.route) {
                    composable(Screens.ScreensLandingPageRoute.route) { LandingPage(navController = navController) }
                }
            }
        }
    }
}


val items = listOf(
    BottomNavigationItem(
        route = Screens.ScreensHomeRoute.route,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        route = Screens.ScreensChatRoute.route,
        title = "Chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
    ),
    BottomNavigationItem(
        route = Screens.ScreensSettingsRoute.route,
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    ),
)