package com.example.myapplication.presentation.settings

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.auth.AccessTokenValidator
import com.example.myapplication.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController,authViewModel: AuthViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Settings Screen")

        Button(
            onClick = {

                coroutineScope.launch {
                    AccessTokenValidator.clearUserCredentials(context = context)
                    authViewModel.setLoggedIn(false)
                }
                navController.navigate(Screens.ScreensLandingPageRoute.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Logout")
        }
    }
}
