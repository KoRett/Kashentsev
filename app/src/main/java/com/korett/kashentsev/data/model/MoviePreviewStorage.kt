package com.korett.kashentsev.data.model

import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.MoviePreview

data class TopMoviesStorage(
    val pagesCount: Int,
    val films: List<MoviePreviewStorage>
)

data class MovieGenre(
    val genre: String
)

data class MovieCountry(
    val country: String
)

data class MovieStorage(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: Int,
    val genres: List<MovieGenre>,
    val countries: List<MovieCountry>,
    val description: String
) {
    fun toDomain(): Movie {
        var allGenres = ""
        genres.forEach {
            allGenres += "${it.genre}, "
        }
        allGenres = allGenres.substring(0, allGenres.length - 2)
        var allCountries = ""
        countries.forEach {
            allCountries += "${it.country}, "
        }
        allCountries = allCountries.substring(0, allCountries.length - 2)
        return Movie(
            movieId = kinopoiskId,
            title = nameRu,
            posterUrl = posterUrl,
            year = year,
            genres = allGenres,
            countries = allCountries,
            description = description
        )
    }
}

data class MoviePreviewStorage(
    val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: Int,
    val genres: List<MovieGenre>
) {
    fun toDomain() = MoviePreview(
        movieId = filmId,
        title = nameRu,
        posterUrlPreview = posterUrlPreview,
        year = year,
        genres = genres.map { it.genre }
    )
}
