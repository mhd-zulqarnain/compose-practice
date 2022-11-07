package com.project.tailor.data.products

import com.project.tailor.api.Result
import com.project.tailor.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val dataSource: ProductsDataSource
) {

     fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            when (val result = dataSource.getProducts()) {
                is Result.Success -> {
                    val data = result.data.products
                    emit(Result.Success(data))
                }
                is Result.Error -> {
                    val data = result.exception
                    emit(Result.Error(data))
                }
                else -> {
                    //not implemented
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }


}