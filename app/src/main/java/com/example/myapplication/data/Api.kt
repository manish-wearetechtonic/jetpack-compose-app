package com.example.myapplication.data

import com.example.myapplication.auth.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class LoginRequestBody(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30 // optional, defaults to 30
)

interface Api {
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequestBody): LoginResponse
}
