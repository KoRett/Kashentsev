package com.korett.kashentsev.domain.model

data class Movie (
    val movieId: Int,
    val title: String,
    val posterUrl: String,
    val year: Int,
    val genres: String,
    val countries: String,
    val description: String
)