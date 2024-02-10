package com.korett.kashentsev.presentation.popular.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.korett.kashentsev.domain.model.ErrorResultModel
import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.PendingResultModel
import com.korett.kashentsev.domain.repository.MoviesRepository
import com.korett.kashentsev.presentation.model.LiveResult
import com.korett.kashentsev.presentation.model.MutableLiveResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MovieInfoViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val movieMutableResult: MutableLiveResult<Movie> = MutableLiveResult(PendingResultModel())
    val movieResult: LiveResult<Movie> = movieMutableResult

    fun getMovie(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                movieMutableResult.value = PendingResultModel()
            }
            val result = moviesRepository.getFilmById(id)
            withContext(Dispatchers.Main) {
                movieMutableResult.value = result
            }
        }
    }

    class Factory @Inject constructor(
        private val moviesRepository: MoviesRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieInfoViewModel(moviesRepository) as T
        }
    }
}