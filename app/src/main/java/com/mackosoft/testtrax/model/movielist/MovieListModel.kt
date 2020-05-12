package com.mackosoft.testtrax.model.movielist

import android.content.Context
import com.mackosoft.testtrax.contract.movielist.MovieListContractInterface
import com.mackosoft.testtrax.network.NetworkApi

class MovieListModel(context: Context) : MovieListContractInterface.Model {

    private val networkApi = NetworkApi(context)


    override suspend fun getMovies() = networkApi.getMovies()

}