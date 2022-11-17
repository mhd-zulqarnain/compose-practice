package com.project.tailor.data.products

import com.project.tailor.api.Result
import com.project.tailor.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val dataSource: ProductsRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) {

    fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            emit(Result.Loading)
            val list = localDataSource.getAll()
            if (list.isEmpty().not())
                emit(Result.Success(list))
            else
                when (val result = dataSource.getProducts()) {
                    is Result.Success -> {
                        val data = result.data.products
                        localDataSource.insertProductList(data).run {
                            emit(Result.Success(data))
                        }
                    }
                    is Result.Error -> {
                        val data = result.exception
                        emit(Result.Error(data))
                    }
                    is Result.Loading -> {
                        emit(Result.Loading)

                    }

                }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }


}