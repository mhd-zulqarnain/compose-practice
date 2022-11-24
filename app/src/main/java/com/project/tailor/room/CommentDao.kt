package com.project.tailor.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.tailor.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Insert
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM comment where productId = :productId")
     fun getAllComments(productId: Int): Flow<List<Comment>>

    @Query("Delete FROM comment where id = :commentId")
    fun deleteComment(commentId: Int)
}