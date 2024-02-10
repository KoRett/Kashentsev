package com.korett.kashentsev.di

import com.korett.kashentsev.data.repository.MoviesRepositoryImpl
import com.korett.kashentsev.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataBindModule {

    @Binds
    fun bindMovieRepositoryImpl_to_MovieRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

}