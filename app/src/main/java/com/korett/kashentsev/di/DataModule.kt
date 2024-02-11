package com.korett.kashentsev.di

import android.content.Context
import com.korett.kashentsev.data.storage.KinopoiskAPI
import com.korett.kashentsev.data.storage.database.LocalDatabase
import com.korett.kashentsev.data.storage.database.dao.MoviePreviewDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideKinopoiskAPI(): KinopoiskAPI = KinopoiskAPI.create()

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context): LocalDatabase = LocalDatabase.create(context)

    @Provides
    @Singleton
    fun provideMoviePreviewDao(localDatabase: LocalDatabase): MoviePreviewDao =
        localDatabase.createMoviePreviewDao()

}