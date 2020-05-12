package com.mackosoft.testtrax.network.model

import com.mackosoft.testtrax.network.model.MovieClipsVersion

data class MovieClips(
    val thumb: String,
    val screen: String,
    val versions: MovieClipsVersion
)