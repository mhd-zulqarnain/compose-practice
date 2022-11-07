package com.project.tailor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.tailor.api.Result
import com.project.tailor.data.products.ProductsRepository
import com.project.tailor.di.CoroutinesDispatcherProvider
import com.project.tailor.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,

    ) : ViewModel() {
    private val _productResult =
        MutableStateFlow<ProductResult>(ProductResult.Loading)
    val productResult: StateFlow<ProductResult> =
        _productResult

    fun getProducts() {
        viewModelScope.launch(dispatcherProvider.io) {
            repository.getProducts().onEach {
                val result = it ?: return@onEach
                when (result) {
                    is Result.Success -> {
                        Log.e("getProducts","Success ${result.data.size}")
                        _productResult.value = ProductResult.ProductList(result.data)
                    }
                    is Result.Loading -> {
                        _productResult.value = ProductResult.Loading
                    }
                    is Result.Error -> {
                        _productResult.value =
                            ProductResult.Error(result.exception.message.orEmpty())
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

//    fun logout() {
//        loginRepository.logout()
//    }
//    fun getUserProfile()= loginRepository.user


    sealed class ProductResult {
        data class ProductList(val list: List<Product>) : ProductResult()
        data class Error(val error: String) : ProductResult()
        object Loading : ProductResult()
    }

}