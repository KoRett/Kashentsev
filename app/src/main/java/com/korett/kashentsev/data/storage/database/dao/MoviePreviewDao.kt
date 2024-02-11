package com.korett.kashentsev.data.storage.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.korett.kashentsev.data.storage.database.entity.MoviePreviewEntity

@Dao
interface MoviePreviewDao {

    @Insert(entity = MoviePreviewEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviePreview(moviePreviewEntity: MoviePreviewEntity)

    @Query("SELECT * FROM ${MoviePreviewEntity.TABLE_NAME}")
    suspend fun getMoviePreviews(): List<MoviePreviewEntity>

    @Delete
    suspend fun removeMoviePreview(moviePreviewEntity: MoviePreviewEntity)

}