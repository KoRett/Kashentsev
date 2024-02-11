package com.korett.kashentsev.data.storage

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.korett.kashentsev.domain.model.MoviePreview
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val kinopoiskAPI: KinopoiskAPI,
    private val favouriteMovieId: List<Int>
) :
    PagingSource<Int, MoviePreview>() {
    override fun getRefreshKey(state: PagingState<Int, MoviePreview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePreview> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = kinopoiskAPI.getPopularMovies(page)
            val moviePreviews = response.body()!!.films
            LoadResult.Page(
                data = moviePreviews.map { it.toDomain(it.filmId in favouriteMovieId) },
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page < response.body()!!.pagesCount) page + 1 else null
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }
}