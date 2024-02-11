package com.korett.kashentsev.data.storage.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.korett.kashentsev.data.storage.database.dao.MoviePreviewDao
import com.korett.kashentsev.data.storage.database.entity.MoviePreviewEntity

@Database(
    entities = [
        MoviePreviewEntity::class
    ],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun createMoviePreviewDao(): MoviePreviewDao

    companion object {
        fun create(context: Context): LocalDatabase {
            return Room.databaseBuilder(
                context = context,
                LocalDatabase::class.java,
                name = "local_database.db"
            ).build()
        }
    }
}