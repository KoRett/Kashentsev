package com.korett.kashentsev.domain.repository

import androidx.paging.PagingData
import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<PagingData<MoviePreview>>

    suspend fun getFilmById(id: Int): ResultModel<Movie>

}