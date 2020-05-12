package com.mackosoft.testtrax.view.moviedetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var binding: FragmentMovieDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieDetailsBinding.bind(view)

        with (MovieDetailsFragmentArgs.fromBundle(requireArguments())) {
            binding.movieTrailerThumb.loadNetworkImage(trailerThumbnail)
            binding.labelMovieSynopsis.text = movieSynopsis

            (activity as? AppCompatActivity)?.supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = movieTitle
            }
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home       -> findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

}