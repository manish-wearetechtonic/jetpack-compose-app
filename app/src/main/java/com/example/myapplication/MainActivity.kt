package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.AccessTokenValidator
import com.example.myapplication.auth.AuthRepository
import com.example.myapplication.presentation.auth.LoginScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        OkHttpClient. Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val isAccessTokenPresent = AccessTokenValidator.isAccessTokenAvailable(LocalContext.current)
                Log.d("Access Token Present","$isAccessTokenPresent")
                NavHost(navController, startDestination = if(isAccessTokenPresent) "home" else "login") {

                    composable("login") {
                        LoginScreen(navController =navController,authRepository=authRepository)
                    }
                    composable("home") {
                        HomeScreen()
                    }
                }

            }
        }
    }
}



