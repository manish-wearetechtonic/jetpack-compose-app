package com.example.myapplication.navigation

import Screens
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myapplication.presentation.home.HomePage
import com.example.myapplication.presentation.home.components.ProductDetailsPage

fun NavGraphBuilder.homeGraph(navController: NavController){
    navigation(
        route = Screens.ScreensHomeRoute.route,
        startDestination = Screens.ScreensProductsRoute.route){

        composable(route = Screens.ScreensProductsRoute.route){
            HomePage(navController = navController)
        }
        composable(route = Screens.ScreensProductsRoute.route + "/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            println("Inside homograph $productId")
            ProductDetailsPage(navController = navController,productId= productId?.toInt() ?: 0)
        }


    }
}