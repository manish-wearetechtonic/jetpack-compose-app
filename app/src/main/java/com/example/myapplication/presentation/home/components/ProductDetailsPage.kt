package com.example.myapplication.presentation.home.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplication.data.model.Product
import com.example.myapplication.presentation.home.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailsPage(navController: NavController, productId: Int) {
    val viewModel = hiltViewModel<ProductViewModel>()
    viewModel.loadProduct(productId = productId)

    val productState by viewModel.product.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel2) {
        viewModel.showErrorToastChannel2.collectLatest { show ->
            if (show) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            ProductDetailsTopBar(navController = navController, productTitle = productState.title)
            if (isLoading) {
                LoadingIndicator()
            } else {
                ProductImage(imageUrl = productState.images.firstOrNull())
                ProductInfo(product = productState)
                ProductImagesList(images = productState.images)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsTopBar(navController: NavController, productTitle: String) {
    TopAppBar(
        title = { Text(productTitle) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProductImage(imageUrl: String?) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        when (imageState) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier.padding(10.dp).fillMaxSize(),
                    painter = imageState.painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ProductInfo(product: Product) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        Text(text = product.title, fontSize = 20.sp)
        Text(text = "\$${product.price}", fontSize = 20.sp)
        Text(text = product.description, fontSize = 14.sp)
    }
}

@Composable
fun ProductImagesList(images: List<String>) {
    val imagePainterList = images.map { imageUrl ->
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .build()
        ).state
    }

    when {
        imagePainterList.any { it is AsyncImagePainter.State.Error } -> {
            LoadingIndicator()
        }
        else -> {
            LazyRow {
                items(imagePainterList.size) { index ->
                    val imageState = imagePainterList[index]
                    if (imageState is AsyncImagePainter.State.Success) {
                        Image(
                            modifier = Modifier.padding(10.dp).height(200.dp),
                            painter = imageState.painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

