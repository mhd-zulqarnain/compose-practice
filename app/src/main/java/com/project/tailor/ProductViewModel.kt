package com.project.tailor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.tailor.api.Result
import com.project.tailor.data.products.ProductsRepositoryImp
import com.project.tailor.di.CoroutinesDispatcherProvider
import com.project.tailor.model.Comment
import com.project.tailor.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepositoryImp,
    private val dispatcherProvider: CoroutinesDispatcherProvider,

    ) : ViewModel() {
    private val _productResult =
        MutableStateFlow<ProductResult>(ProductResult.Loading)
    val productResult: StateFlow<ProductResult> =
        _productResult

    private val _commentResult =
        MutableStateFlow<List<Comment>>(arrayListOf())
    val commentResult: StateFlow<List<Comment>> =
        _commentResult

    private val _productDetails =
        MutableStateFlow<Product?>(null)
    val productDetails: StateFlow<Product?> =
        _productDetails

    private var job: Job? = null
    private var detailsJob: Job? = null
    private var allProductJob: Job? = null
    private var searchJob: Job? = null

    fun filterProducts(keyword: String, filter: Boolean? = null) {
        allProductJob?.cancel()
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcherProvider.io) {
            repository.filterProduct(keyword).cancellable().collect {
                if (it.isEmpty())
                    _productResult.value =
                        ProductResult.Error("No product found with $keyword title")
                else {
                    if (filter != null)
                        _productResult.value =
                            ProductResult.ProductList(it.filter { it.favorite == filter })
                    else
                        _productResult.value = ProductResult.ProductList(it)

                }
            }
        }
    }

    /**
     * get data from local db if the db is not empty
     * */
    fun getProducts() {
        allProductJob = viewModelScope.launch(dispatcherProvider.io) {
                repository.getProductFromDB().cancellable().collect {
                    if (it.isEmpty())
                        repository.getProducts().onEach {
                            val result = it ?: return@onEach
                            when (result) {
                                is Result.Success -> {
                                    Log.e("getProducts", "Success ${result.data.size}")
                                }
                                is Result.Loading -> {
                                    _productResult.value = ProductResult.Loading
                                }
                                is Result.Error -> {
                                    _productResult.value =
                                        ProductResult.Error(result.exception.message.orEmpty())
                                }
                            }
                        }.collect()
                    else
                        _productResult.value = ProductResult.ProductList(it)
                }
        }

    }

    fun addComment(comment: String, productId: Int?) {
        viewModelScope.launch(dispatcherProvider.io) {
            productId?.let {
                val tmp = Comment(productId = productId, comment = comment)
                repository.addComment(tmp)
            }
        }
    }

    fun getComments(productId: Int?) {
        Log.e("getComments", "upper productId: $productId")
        job?.cancel()
        productDetails.value?.id?.let { id ->
            job = viewModelScope.launch {
                repository.getComments(id).cancellable().collect {
                    Log.e("getComments", "productId: $id")
                    _commentResult.value = it
                }
            }
        }
    }

    fun deleteComment(productId: Int?, commentId: Int) {
        productId?.let {
            viewModelScope.launch(dispatcherProvider.io) {
                repository.deleteComment(commentId)
            }
        }
    }

    fun setProductDetails(product: Product) {
        job?.cancel()
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch(dispatcherProvider.io) {
            repository.getSingleProduct(product.id ?: 0).cancellable().collect {
                _productDetails.value = it
            }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch(dispatcherProvider.io) {
            repository.toggleFavorite(product)
        }
    }

    sealed class ProductResult {
        data class ProductList(val list: List<Product>) : ProductResult()
        data class Error(val error: String) : ProductResult()
        object Loading : ProductResult()
    }

}