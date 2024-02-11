package com.korett.kashentsev.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.korett.kashentsev.data.storage.KinopoiskAPI
import com.korett.kashentsev.data.storage.MoviePagingSource
import com.korett.kashentsev.data.storage.database.dao.MoviePreviewDao
import com.korett.kashentsev.data.storage.database.entity.MoviePreviewEntity
import com.korett.kashentsev.domain.model.ErrorResultModel
import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.model.ResultModel
import com.korett.kashentsev.domain.model.SuccessResultModel
import com.korett.kashentsev.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGE_SIZE = 20

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val kinopoiskAPI: KinopoiskAPI,
    private val moviePreviewDao: MoviePreviewDao
) : MoviesRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<MoviePreview>> {
        val favouriteMoviePreviews = moviePreviewDao.getMoviePreviews().map { it.toDomain().movieId }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviePagingSource(kinopoiskAPI, favouriteMoviePreviews) }
        ).flow
    }

    override suspend fun getFilmById(id: Int): ResultModel<Movie> {
        return try {
            val response = kinopoiskAPI.getFilmById(id)
            if (response.isSuccessful)
                SuccessResultModel(response.body()!!.toDomain())
            else
                ErrorResultModel(IOException())
        } catch (e: IOException) {
            ErrorResultModel(e)
        } catch (e: HttpException) {
            ErrorResultModel(e)
        }
    }

    override suspend fun getFavouriteMovies(): ResultModel<List<MoviePreview>> {
        val moviePreviews = moviePreviewDao.getMoviePreviews().map { it.toDomain() }
        return SuccessResultModel(moviePreviews)
    }

    override suspend fun removeMoviePreview(moviePreview: MoviePreview) {
        val moviePreviewEntity = MoviePreviewEntity(
            id = moviePreview.movieId,
            title = moviePreview.title!!,
            genre = moviePreview.genre,
            posterUrl = moviePreview.posterUrlPreview,
            year = moviePreview.year
        )
        moviePreviewDao.removeMoviePreview(moviePreviewEntity)
    }

    override suspend fun saveMoviewPreviewToFavourite(moviePreview: MoviePreview) {
        val moviePreviewEntity = MoviePreviewEntity(
            id = moviePreview.movieId,
            title = moviePreview.title!!,
            genre = moviePreview.genre,
            posterUrl = moviePreview.posterUrlPreview,
            year = moviePreview.year
        )
        moviePreviewDao.addMoviePreview(moviePreviewEntity)
    }

}