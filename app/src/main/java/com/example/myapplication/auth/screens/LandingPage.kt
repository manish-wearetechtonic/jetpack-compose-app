package com.example.myapplication.auth.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.auth.AccessTokenValidator
import com.example.myapplication.auth.AuthViewModel
import com.example.myapplication.navigation.BottomNavigation
import kotlinx.coroutines.launch

@Composable
fun LandingPage(navController: NavController,authViewModel: AuthViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    SideEffect {
        coroutineScope.launch {
            authViewModel.setLoggedIn(AccessTokenValidator.isValidToken(context))
        }
    }

    if (isLoggedIn.value) {
        BottomNavigation( )
    } else {
        LoginScreen(navController)
    }
}
