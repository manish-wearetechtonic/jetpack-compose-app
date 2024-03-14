package com.example.myapplication.navigation

import Screens
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myapplication.auth.screens.LandingPage
import com.example.myapplication.auth.screens.LoginScreen
import com.example.myapplication.items
import com.example.myapplication.presentation.chat.ChatScreen
import com.example.myapplication.presentation.home.HomePage
import com.example.myapplication.presentation.settings.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    val isSelected = item.route == navController.currentDestination?.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon
                                else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screens.ScreensHomeRoute.route) {
            composable(Screens.ScreensHomeRoute.route) { HomePage(navController = navController) }
            composable(Screens.ScreensChatRoute.route) { ChatScreen() }
            composable(Screens.ScreensSettingsRoute.route) { SettingsScreen() }
        }
    }
}



