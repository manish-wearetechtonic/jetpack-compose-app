package com.example.myapplication.auth

import com.example.myapplication.auth.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.data.Result

interface AuthRepository {
    suspend fun login(username: String, password: String): Flow<Result<LoginResponse>>
}