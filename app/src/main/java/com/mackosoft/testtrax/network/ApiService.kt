package com.mackosoft.testtrax.network

import com.mackosoft.testtrax.model.Movie
import retrofit2.http.GET

interface ApiService {

    @GET("movies.json")
    suspend fun getMovies(): List<Movie>

}