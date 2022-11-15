package com.project.tailor.data.products


import com.project.tailor.model.Product

import com.project.tailor.room.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao
) {
//    suspend fun getProducts(): Result<Products> {
//        return when (networkHandler.isConnected) {
//            true -> {
//                safeApiCall(
//                    call = {
//                        callSampleApi()
//                    },
//                    errorMessage = context.getString(R.string.error_msg)
//                )
//            }
//            false -> {
//                Result.Error(IOException(context.getString(R.string.failure_network_connection)))
//            }
//        }
//    }
//    private suspend fun callSampleApi(
//    ): Result<Products> {
//        return flexApi.getProducts().processResponse()
//    }

    fun insertProductList(list:ArrayList<Product>){
        list.forEach {
            productDao.insertProduct(it)
        }
    }
    fun getAll():List<Product>{
           return productDao.getAll()
    }
}