package com.korett.kashentsev.data.storage

import com.korett.kashentsev.data.model.MovieStorage
import com.korett.kashentsev.data.model.TopMoviesStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskAPI {

    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularMovies(@Query("page") page: Int) : Response<TopMoviesStorage>

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmById(@Path("id") id: Int) : Response<MovieStorage>

    companion object {
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

        fun create(): KinopoiskAPI {
            val clientBuilder = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            clientBuilder.addInterceptor(interceptor)

            clientBuilder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(name = "x-api-key", value = API_KEY)
                    .build()
                return@addInterceptor chain.proceed(request)
            }

            val retrofit = Retrofit.Builder().baseUrl("https://kinopoiskapiunofficial.tech")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
            return retrofit.create(KinopoiskAPI::class.java)
        }
    }
}