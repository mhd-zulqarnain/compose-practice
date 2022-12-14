package com.project.tailor.data.products


import com.project.tailor.model.Comment
import com.project.tailor.model.Product
import com.project.tailor.room.CommentDao
import com.project.tailor.room.ProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val commentDao: CommentDao
) {
    fun insertProductList(list: ArrayList<Product>) {
        list.forEach {
            productDao.insertProduct(it)
        }
    }

    fun getAll(): Flow<List<Product>> {
        return productDao.getAll()
    }

    fun filterProduct(param :String): Flow<List<Product>> {
        return productDao.filterProduct(param)
    }

//    fun getProductsWithComments(): List<Product> {
//        val data = productDao.getProductsWithComments()
//        val map: ArrayList<Product> = arrayListOf()
//        data.forEach { combined ->
//            map.add(combined.parent.copy(
//                    commentsList = combined.comments,
//            ))
//        }
//        return map
//    }

    fun addComment(comment: Comment) {
        commentDao.insertComment(comment)
    }

    fun getComments(productId: Int): Flow<List<Comment>> {
        return commentDao.getAllComments(productId)
    }

    fun deleteComment(commentId: Int) {
        commentDao.deleteComment(commentId)
    }

    fun toggleFavorite(product: Product) {
        productDao.toggleFavorite(product.favorite.not(), product.id ?: 0)
    }

    fun getSingleProduct(id: Int): Flow<Product> =
        productDao.getSingleProduct(id)

}