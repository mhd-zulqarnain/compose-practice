package com.project.tailor.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.tailor.model.Product

@Dao
interface ProductDao {
    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>
}