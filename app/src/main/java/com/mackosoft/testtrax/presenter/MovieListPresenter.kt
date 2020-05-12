package com.mackosoft.testtrax.presenter

import com.mackosoft.testtrax.contract.movielist.MovieListContractInterface
import com.mackosoft.testtrax.model.movielist.MovieListModel
import com.mackosoft.testtrax.network.Result
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

// MVP + coroutines inspired from https://faithlife.codes/blog/2018/11/android-mvp-with-coroutines/
class MovieListPresenter(view: MovieListContractInterface.View) :
    MovieListContractInterface.Presenter,
    CoroutineScope {

    private val view = WeakReference(view)
    private val model = MovieListModel(view.provideApplicationContext())

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO


    override fun getMovies() {
        view.get()?.isLoadingMovie(true)

        launch {
            val movies = model.getMovies()

            withContext(Dispatchers.Main) {
                view.get()?.let {
                    if (movies is Result.Success) {
                        it.showMovieList(movieList = movies.data)
                    } else {
                        it.onFailedToLoadMovies()
                    }
                    it.isLoadingMovie(false)
                }
            }
        }
    }


    override fun cleanUp() {
        view.clear()
        job.cancel()
    }
}