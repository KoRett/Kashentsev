package com.korett.kashentsev.presentation.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularMoviesViewModel(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    var movies: Flow<PagingData<MoviePreview>> = moviesRepository.getPopularMovies().cachedIn(viewModelScope)

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PopularMoviesViewModel(moviesRepository) as T
        }
    }
}