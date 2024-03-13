package com.example.myapplication.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem (
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)