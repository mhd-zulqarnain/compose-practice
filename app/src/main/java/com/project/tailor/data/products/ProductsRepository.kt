package com.project.tailor.data.products

import com.project.tailor.api.Result
import com.project.tailor.model.Comment
import com.project.tailor.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getProductFromDB(): Flow<List<Product>>
    fun filterProduct(param :String): Flow<List<Product>>
    fun getProducts(): Flow<Result<List<Product>>>
    fun addComment(comment: Comment)
    fun getComments(productId: Int): Flow<List<Comment>>
    fun deleteComment(commentId: Int)
    fun toggleFavorite(product: Product)
    fun getSingleProduct(id: Int): Flow<Product>

}