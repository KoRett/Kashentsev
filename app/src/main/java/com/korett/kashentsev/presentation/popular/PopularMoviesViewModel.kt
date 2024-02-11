package com.korett.kashentsev.presentation.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    val mutableMoviePreviews: MutableStateFlow<PagingData<MoviePreview>> =
        MutableStateFlow(PagingData.empty())
    val moviePreviews: StateFlow<PagingData<MoviePreview>> = mutableMoviePreviews

    init {
        getMoviePreviews()
    }

    fun saveMoviePreviewToFavourite(moviePreview: MoviePreview) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.saveMoviewPreviewToFavourite(moviePreview)
        }
    }

    fun removeMovieFromFavourite(moviePreview: MoviePreview) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.removeMoviePreview(moviePreview)
        }
    }

    private fun getMoviePreviews() {
        viewModelScope.launch {
            mutableMoviePreviews.value = moviesRepository.getPopularMovies()
                .cachedIn(viewModelScope)
                .first()
        }
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularMoviesViewModel(moviesRepository) as T
        }
    }
}