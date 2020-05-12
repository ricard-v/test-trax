package com.mackosoft.testtrax.model

data class Movie(
    val page: MoviePage,
    val details: MovieDetails,
    val clips: List<MovieClips>
)