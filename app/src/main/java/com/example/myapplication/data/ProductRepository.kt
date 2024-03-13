package com.example.myapplication.data

import com.example.myapplication.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun  getProductList(): Flow<Result<List<Product>>>
    suspend fun  getProductById(productId: Int): Flow<Result<Product>>
}