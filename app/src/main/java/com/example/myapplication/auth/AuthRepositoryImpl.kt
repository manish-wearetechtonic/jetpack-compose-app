package com.example.myapplication.auth

import android.util.Log
import com.example.myapplication.auth.model.LoginResponse
import com.example.myapplication.data.Api
import com.example.myapplication.data.LoginRequestBody
import com.example.myapplication.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class AuthRepositoryImpl(private val api: Api) : AuthRepository {
    override suspend fun login(username: String, password: String): Flow<Result<LoginResponse>> {
        return flow {
            val loginResponse = try {
                val requestBody = LoginRequestBody(username, password)
                Log.d("Body request", "$requestBody")
                val loginResponse = api.login(requestBody)
                Log.d("Login Response", loginResponse.token)
                api.login(requestBody)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = e.localizedMessage ?: "Unexpected error"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error logging in"))
                return@flow
            } catch (e: Exception) {
                Log.d("Body request", "Error: ${e.localizedMessage}")
                e.printStackTrace()
                emit(Result.Error(message = "Something went wrong\n${e.localizedMessage}"))
                return@flow
            }
            emit(Result.Success(loginResponse))
        }
    }
}
