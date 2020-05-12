package com.mackosoft.testtrax.view.movielist

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.contract.movielist.MovieListContractInterface
import com.mackosoft.testtrax.databinding.FragmentMovieListBinding
import com.mackosoft.testtrax.network.model.Movie
import com.mackosoft.testtrax.presenter.MovieListPresenter

class MovieListFragment : Fragment(R.layout.fragment_movie_list),
    MovieListContractInterface.View {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var presenter: MovieListPresenter

    private val adapter = MovieListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieListBinding.bind(view)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MovieListPresenter(this) // safe to get activity context in `provideApplicationContext`

        binding.refresher.setOnRefreshListener {
            presenter.getMovies()
        }

        binding.movieList.adapter = adapter
        binding.movieList.setHasFixedSize(true)
        binding.movieList.addItemDecoration(MovieListDecoration())

        presenter.getMovies()
    }


    override fun isLoadingMovie(isLoading: Boolean) {
        binding.refresher.isRefreshing = isLoading
    }


    override fun provideApplicationContext(): Application = requireActivity().application


    override fun showMovieList(movieList: List<Movie>) {
        adapter.submitList(movieList)
    }


    override fun onFailedToLoadMovies() {
        // TODO
    }


    override fun onDestroyView() {
        super.onDestroyView()

        presenter.cleanUp()
    }
}