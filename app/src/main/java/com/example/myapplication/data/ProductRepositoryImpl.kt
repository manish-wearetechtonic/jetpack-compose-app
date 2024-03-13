package com.example.myapplication.data

import com.example.myapplication.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class ProductRepositoryImpl (private  val  api: Api) : ProductRepository {
    override suspend fun getProductList(): Flow<Result<List<Product>>>{
        return  flow {
            val  productFromApi = try {
                api.getProductsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = e.localizedMessage))
                return@flow

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading data"))
                return@flow

            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = " Something went wording\n${e.localizedMessage}"))
                return@flow

            }
            emit(Result.Success(productFromApi.products))
        }
    }

    override suspend fun getProductById(productId: Int): Flow<Result<Product>> {
        return  flow {
            val  productFromApi = try {
                api.getProductById(productId = productId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = e.localizedMessage ?: "Unexpected error"))
                return@flow

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading data"))
                return@flow

            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = " Something went wording\n${e.localizedMessage}"))
                return@flow

            }
            println("Product RepositoryImpl ${productFromApi.title}")
            emit(Result.Success(productFromApi))
        }
    }
}