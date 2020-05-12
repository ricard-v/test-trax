package com.mackosoft.testtrax.network.model

import com.google.gson.annotations.SerializedName

data class MovieHerosImage(
    @SerializedName("imageurl")
    val imageUrl: String
)