package com.korett.kashentsev.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.model.PendingResultModel
import com.korett.kashentsev.domain.repository.MoviesRepository
import com.korett.kashentsev.presentation.model.LiveResult
import com.korett.kashentsev.presentation.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteMoviesViewModel(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val movieMutableResult: MutableLiveResult<List<MoviePreview>> = MutableLiveResult(PendingResultModel())
    val movieResult: LiveResult<List<MoviePreview>> = movieMutableResult

    fun getFavouriteMoviePreviews() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = moviesRepository.getFavouriteMovies()
            withContext(Dispatchers.Main) {
                movieMutableResult.value = movies
            }
        }
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavouriteMoviesViewModel(moviesRepository) as T
        }
    }
}