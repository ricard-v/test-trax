package com.mackosoft.testtrax.contract.movielist

import android.app.Application
import com.mackosoft.testtrax.network.Result
import com.mackosoft.testtrax.network.model.Movie

// from https://medium.com/mindorks/android-mvp-pattern-debunked-for-beginners-kotlin-c56c888222e0
interface MovieListContractInterface {

    interface View {
        fun provideApplicationContext() : Application
        fun isLoadingMovie(isLoading: Boolean)
        fun showMovieList(movieList: List<Movie>)
        fun onFailedToLoadMovies()
    }


    interface Presenter {
        fun getMovies()
        fun cleanUp()
    }


    interface Model {
        suspend fun getMovies(): Result<List<Movie>>
    }
}