package com.project.tailor.data.products

import com.project.tailor.api.Result
import com.project.tailor.model.Comment
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

    fun getProductFromDB(): Flow<List<Product>> {
        return localDataSource.getAll()
    }

    fun filterProduct(param :String): Flow<List<Product>> {
        return localDataSource.filterProduct(param)
    }


    fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
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

    fun addComment(comment: Comment) {
        localDataSource.addComment(comment)
    }

    fun getComments(productId: Int): Flow<List<Comment>> {
        return localDataSource.getComments(productId)
    }

    fun deleteComment(commentId: Int) {
        localDataSource.deleteComment(commentId)
    }

    fun toggleFavorite(product: Product) {
        localDataSource.toggleFavorite(product)
    }

    fun getSingleProduct(id: Int): Flow<Product> =
        localDataSource.getSingleProduct(id)

}