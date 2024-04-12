package com.example.myapplication.auth.screens

import Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.myapplication.auth.AccessTokenValidator
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val accessToken = "dummy_access_token"
                val refreshToken = "dummy_refresh_token"
                val accessTokenExpiry = System.currentTimeMillis() + 2 * 60 * 60 * 1000 // 2 hours
                val refreshTokenExpiry = System.currentTimeMillis() + 24 * 60 * 60 * 1000 // 24 hours

                coroutineScope.launch {
                    AccessTokenValidator.saveUserCredentials(
                        context = context,
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                        accessTokenExpiry = accessTokenExpiry,
                        refreshTokenExpiry = refreshTokenExpiry
                    )
                }
                navController.navigate(Screens.ScreensLandingPageRoute.route){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }

            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Login")
        }
    }
}
