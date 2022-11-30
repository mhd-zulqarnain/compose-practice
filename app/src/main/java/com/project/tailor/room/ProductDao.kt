package com.project.tailor.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.tailor.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Query("UPDATE product SET favorite = :favorite where id =:id ")
     fun toggleFavorite(favorite: Boolean, id: Int)

    @Query("SELECT * FROM product where id = :productId")
    fun getSingleProduct(productId: Int): Flow<Product>

//    @Query("SELECT * FROM product JOIN Comment ON comment.productId = product.id")
//    abstract fun getProductsWithComments(): List<Combined>
}