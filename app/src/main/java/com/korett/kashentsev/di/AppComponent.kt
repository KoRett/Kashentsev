package com.korett.kashentsev.di

import android.content.Context
import com.korett.kashentsev.presentation.popular.PopularMoviesFragment
import com.korett.kashentsev.presentation.popular.info.MovieInfoFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        DataBindModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(popularMoviesFragment: PopularMoviesFragment)
    fun inject(popularMoviesFragment: MovieInfoFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}