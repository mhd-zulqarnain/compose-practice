package com.project.tailor.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.tailor.model.Comment
import com.project.tailor.model.Product

@Database(entities = [Product::class,Comment::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun commentDao(): CommentDao
}