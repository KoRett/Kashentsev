package com.korett.kashentsev.domain.model

data class MoviePreview(
    val movieId: Int,
    val title: String?,
    val posterUrlPreview: String,
    val year: Int,
    val genre: String,
    var isFavourite: Boolean
)