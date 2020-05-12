package com.mackosoft.testtrax.model

import com.google.gson.annotations.SerializedName

data class MoviePage(
    @SerializedName("movie_title")
    val title: String,
    @SerializedName("release_copy")
    val releaseData: String
)