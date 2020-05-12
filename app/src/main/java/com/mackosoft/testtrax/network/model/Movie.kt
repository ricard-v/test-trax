package com.mackosoft.testtrax.network.model

import com.mackosoft.testtrax.network.model.MovieClips
import com.mackosoft.testtrax.network.model.MovieDetails
import com.mackosoft.testtrax.network.model.MoviePage

data class Movie(
    val page: MoviePage,
    val heros: MovieHeros,
    val details: MovieDetails,
    val clips: List<MovieClips>
)