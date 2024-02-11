package com.korett.kashentsev.data.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.korett.kashentsev.data.storage.database.entity.MoviePreviewEntity.Companion.TABLE_NAME
import com.korett.kashentsev.domain.model.MoviePreview

@Entity(tableName = TABLE_NAME)
data class MoviePreviewEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val genre: String,
    @ColumnInfo(name = "poster_url") val posterUrl: String,
    val year: Int
) {

    fun toDomain() = MoviePreview(
        movieId = id,
        title = title,
        posterUrlPreview = posterUrl,
        year = year,
        genre = genre,
        isFavourite = true
    )

    companion object {
        const val TABLE_NAME = "movie_preview_table"
    }
}