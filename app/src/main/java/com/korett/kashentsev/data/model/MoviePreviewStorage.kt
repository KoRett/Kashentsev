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
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String,
    val year: Int,
    val genres: List<MovieGenre>?,
    val countries: List<MovieCountry>?,
    val description: String?
) {
    fun toDomain(): Movie {
        var allGenres = ""
        if (genres != null) {
            genres.forEach {allGenres += "${it.genre}, " }
            allGenres = allGenres.substring(0, allGenres.length - 2)
        } else
            allGenres = "-"

        var allCountries = ""
        if (countries != null) {
            countries.forEach { allCountries += "${it.country}, " }
            allCountries = allCountries.substring(0, allCountries.length - 2)
        }
        else
            allCountries = "-"

        return Movie(
            movieId = kinopoiskId,
            title = nameRu ?: nameEn ?: "-",
            posterUrl = posterUrl,
            year = year,
            genres = allGenres,
            countries = allCountries,
            description = description ?: "*Описание отсутствует :c*"
        )
    }
}

data class MoviePreviewStorage(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrlPreview: String,
    val year: Int,
    val genres: List<MovieGenre>
) {
    fun toDomain(isFavourite: Boolean) = MoviePreview(
        movieId = filmId,
        title = nameRu ?: nameEn ?: "-",
        posterUrlPreview = posterUrlPreview,
        year = year,
        genre = genres[0].genre,
        isFavourite = isFavourite
    )
}
