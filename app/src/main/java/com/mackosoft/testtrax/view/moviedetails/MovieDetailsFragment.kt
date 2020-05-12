package com.mackosoft.testtrax.view.moviedetails

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.databinding.FragmentMovieDetailsBinding
import com.mackosoft.testtrax.utils.getCache

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {


    private companion object {
        const val SAVED_STATE_VIDEO_PLAYER_POSITION = "key_video_player_position"
        const val SAVED_STATE_VIDEO_PLAYER_WAS_PLAYING = "key_video_player_playing"
    }


    private lateinit var binding: FragmentMovieDetailsBinding

    private var player: ExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieDetailsBinding.bind(view)

        with (MovieDetailsFragmentArgs.fromBundle(requireArguments())) {
            binding.movieTrailerThumb.loadNetworkImage(trailerThumbnail)
            binding.labelMovieSynopsis.text = movieSynopsis

            prepareVideoPlayer(trailerUrl, savedInstanceState)

            (activity as? AppCompatActivity)?.supportActionBar?.let {
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    it.show()
                    leaveFullScreen()
                } else {
                    it.hide()
                    goFullScreen()
                }
                it.setDisplayHomeAsUpEnabled(true)
                it.title = movieTitle
            }
        }

        binding.imageButtonPlay.setOnClickListener {
            TransitionManager.beginDelayedTransition(view.parent as ViewGroup)
            binding.videoPlayer.visibility = View.VISIBLE
            binding.movieTrailerThumb.visibility = View.INVISIBLE
            binding.imageButtonPlay.visibility = View.INVISIBLE

            player?.playWhenReady = true
        }

        if (savedInstanceState?.getBoolean(SAVED_STATE_VIDEO_PLAYER_WAS_PLAYING) == true) { // was playing video
            binding.videoPlayer.visibility = View.VISIBLE
            binding.movieTrailerThumb.visibility = View.INVISIBLE
            binding.imageButtonPlay.visibility = View.INVISIBLE
        }
    }


    private fun goFullScreen() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    private fun leaveFullScreen() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home       -> findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun prepareVideoPlayer(trailerUrl: String, savedInstanceState: Bundle?) {
        val sourceFactory = DefaultHttpDataSourceFactory(
            Util.getUserAgent(requireContext(), getString(R.string.app_name)),
            null,
            3000,
            3000,
            true
        )

        val cacheDataSourceFactory = CacheDataSourceFactory(getCache(requireContext()), sourceFactory)
        val source = ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(Uri.parse(trailerUrl))


        with(SimpleExoPlayer.Builder(requireContext()).build()) {
            repeatMode = Player.REPEAT_MODE_OFF
            prepare(source)

            this.addListener(object : Player.EventListener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    takeIf { playbackState == Player.STATE_READY
                            && savedInstanceState!= null
                            && savedInstanceState.containsKey(SAVED_STATE_VIDEO_PLAYER_POSITION) }?.apply {
                        this@with.seekTo(savedInstanceState!![SAVED_STATE_VIDEO_PLAYER_POSITION] as Long)
                    }
                }
            })

            player = this
            binding.videoPlayer.player = this
        }
    }


    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        player?.let {
            outState.putLong(SAVED_STATE_VIDEO_PLAYER_POSITION, it.currentPosition)
            outState.putBoolean(SAVED_STATE_VIDEO_PLAYER_WAS_PLAYING, it.currentPosition > 0)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        player?.release()
    }

}