package com.example.myapplication.presentation.home

import Screens
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.home.components.ProductCard
import com.example.myapplication.presentation.home.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomePage(navController: NavController) {
    val viewModel = hiltViewModel<ProductViewModel>()

    viewModel.loadProducts()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val productList = viewModel.products.collectAsState().value
        val context = LocalContext.current

        LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
            viewModel.showErrorToastChannel.collectLatest { show ->
                if (show) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (productList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(productList.size) { index ->
                    val product = productList[index]
                    ProductCard(product = product, onItemClick = {
                        navController.navigate(Screens.ScreensProductsRoute.route + "/${product.id}")
                    })
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}