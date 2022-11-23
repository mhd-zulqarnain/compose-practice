package com.project.tailor.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.tailor.model.Comment

@Dao
interface CommentDao {
    @Insert
    fun insertComment(comment: Comment)

    @Query("SELECT * FROM comment where productId = :productId")
    fun getAllComments(productId: Int): List<Comment>
}