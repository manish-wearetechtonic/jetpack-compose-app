package com.example.myapplication.data

import com.example.myapplication.data.model.Product
import com.example.myapplication.data.model.Products
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {


    @GET("products")
    suspend fun getProductsList(): Products

    @GET("product/{productId}")
    suspend fun getProductById(@Path("productId") productId: Int): Product


    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}