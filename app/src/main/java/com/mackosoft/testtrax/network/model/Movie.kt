package com.mackosoft.testtrax.network.model

data class Movie(
    val page: MoviePage,
    val heros: MovieHeros,
    val details: MovieDetails,
    val clips: List<MovieClips>
)