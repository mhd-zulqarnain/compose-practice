package com.project.tailor.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class Products(
    @SerializedName("products") var products: ArrayList<Product> = arrayListOf()
)

@Entity
data class Product(
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("discountPercentage") var discountPercentage: Double? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("images") var images: List<String> = arrayListOf(),
    @SerializedName("favorite") var favorite: Boolean = false
)

@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val productId: Int? = null,
    val comment: String = "",
    val timeStamp: Long = System.currentTimeMillis(),
) {
//    companion object {
//
//        fun buildParentsWithComment(ProductsWithComments: List<ProductsWithComments>): List<Combined> {
//            var rv: ArrayList<Combined> = arrayListOf()
//            if (ProductsWithComments.isEmpty()) return rv
//            for (f: ProductsWithComments in ProductsWithComments) {
//                addComment(rv, getOrAddParent(rv, f), f)
//            }
//            return rv
//        }
//
//        private fun getOrAddParent(built: ArrayList<Combined>, f: ProductsWithComments): Int {
//            for (i in 0 until built.size) {
//                if (built[i].parent.id == f.product.id) {
//                    return i
//                }
//            }
//            val newCombined: Combined = Combined(f.product, emptyList())
//            built.add(newCombined)
//            return built.size - 1
//        }
//
//        private fun addComment(built: ArrayList<Combined>, parentIx: Int, f: ProductsWithComments) {
//            val currentComment: ArrayList<Comment> = arrayListOf()
//            currentComment.addAll(built[parentIx].comments)
//            currentComment.add(f.comment)
//            built[parentIx] = Combined(parent = built[parentIx].parent, currentComment)
//        }
//    }
}

//
//data class ProductsWithComments(
//        @Embedded
//        val product: Product,
//        @Embedded
//        val comment: Comment
//)
//
//data class Combined(
//        @Embedded val parent: Product,
//        @Relation(
//                parentColumn = "productId",
//                entityColumn = "Id"
//        )
//        val comments: List<Comment>
//)