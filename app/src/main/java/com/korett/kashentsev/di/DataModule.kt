package com.korett.kashentsev.di

import com.korett.kashentsev.data.storage.KinopoiskAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideKinopoiskAPI(): KinopoiskAPI = KinopoiskAPI.create()

}