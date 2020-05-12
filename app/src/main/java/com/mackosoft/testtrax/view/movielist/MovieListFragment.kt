package com.mackosoft.testtrax.view.movielist

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.contract.movielist.MovieListContractInterface
import com.mackosoft.testtrax.databinding.FragmentMovieListBinding
import com.mackosoft.testtrax.network.model.Movie
import com.mackosoft.testtrax.presenter.MovieListPresenter

class MovieListFragment : Fragment(R.layout.fragment_movie_list),
    MovieListContractInterface.View {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var presenter: MovieListPresenter

    private val adapter = MovieListAdapter { movie -> onMovieSelected(movie) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieListBinding.bind(view)

        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.title = getString(R.string.app_name)
        }
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
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.fragment_movie_list_error_loading_title)
            .setMessage(R.string.fragment_movie_list_error_loading_message)
            .setNeutralButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.fragment_movie_list_error_loading_retry) { dialog, _ ->
                dialog.dismiss()
                presenter.getMovies()
            }.show()
    }


    private fun onMovieSelected(movie: Movie) {
        val directions = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
            movie.page.title,
            movie.details.locale.en.synopsis,
            movie.clips[0].versions.enus.sizes.hd720.srcAlt, // TODO handle no trailer
            movie.clips[0].thumb
        )

        findNavController().navigate(directions)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        presenter.cleanUp()
    }
}