package com.example.myapplication.auth.screens

import Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.myapplication.auth.AccessTokenValidator
import kotlinx.coroutines.launch

@Composable
fun LandingPage(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    SideEffect {
        coroutineScope.launch {
            val isLoggedIn = AccessTokenValidator.isValidToken(context)
            if (isLoggedIn) {
                navController.navigate(Screens.ScreensProductsRoute.route)
            } else {
                navController.navigate(Screens.ScreensLoginRoute.route)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Checking login status...")
    }
}
