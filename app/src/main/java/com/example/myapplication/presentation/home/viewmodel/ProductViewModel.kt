package com.example.myapplication.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ProductRepository
import com.example.myapplication.data.Result
import com.example.myapplication.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private  val productRepository: ProductRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val  products = _products.asStateFlow()

    private  val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            productRepository.getProductList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        Log.d("error@@@",result.message.toString())
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { products ->
                            _products.update { products }
                        }
                    }
                }
                _isLoading.value = false
            }
        }
    }



private val _product = MutableStateFlow(Product()) // Use the default constructor
    val product: StateFlow<Product> get() = _product


    private  val _showErrorToastChannel2 = Channel<Boolean>()
    val showErrorToastChannel2 = _showErrorToastChannel.receiveAsFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.getProductById(productId = productId).collectLatest { result ->
                println("Get product by id is called ${result}")
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel2.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { product ->
                            println("Data fetched ${product.title}")
                            _product.value = product  // Update the _product state with the new product
                        }
                    }
                }
            }
        }
    }

}