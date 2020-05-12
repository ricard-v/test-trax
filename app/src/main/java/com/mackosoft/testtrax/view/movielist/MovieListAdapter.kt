package com.mackosoft.testtrax.view.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.databinding.MovieItemBinding
import com.mackosoft.testtrax.network.model.Movie

class MovieListAdapter(
    private val onMovieSelected: (movie: Movie) -> Unit
) : ListAdapter<Movie, MovieListAdapter.MovieItemViewHolder>(itemDifferCallback) {


    private companion object {
        private val itemDifferCallback = object : DiffUtil.ItemCallback<Movie?>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie
            ) = oldItem.page.title != newItem.page.title

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ) = oldItem != newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    // ViewHolder

    inner class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = MovieItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                currentList.getOrNull(adapterPosition)?.let(onMovieSelected)
            }
        }

        fun bind(movie: Movie) {
            binding.movieThumbnail.loadNetworkImage(movie.heros.movieHerosImage.imageUrl)
            binding.labelMovieTitle.text = movie.page.title
            binding.labelMovieReleaseDate.text = movie.page.releaseData
        }
    }
}