package com.blinxio.technicalassignment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.R
import com.blinxio.technicalassignment.databinding.FragmentMovieDetailsBinding
import com.blinxio.technicalassignment.details.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var binding: FragmentMovieDetailsBinding? = null
    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.movieDetails.collect { movieDetails ->
                    if (movieDetails != null) {
                        binding?.apply {
                            movieDetailsBackdrop.load("${BuildConfig.IMAGE_URL}/w500/${movieDetails.backdropPath}")
                            movieDetailsPoster.load("${BuildConfig.IMAGE_URL}/w500/${movieDetails.posterPath}")
                            movieDetailsSummary.text = movieDetails.overview
                            movieDetailsTitle.text = movieDetails.title
                            movieDetailsYear.text = movieDetails.releaseDate
                            movieDetailsTagline.text = movieDetails.tagline
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}