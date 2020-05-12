package com.mackosoft.testtrax.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var binding: FragmentMovieDetailsBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieDetailsBinding.bind(view)
    }


}