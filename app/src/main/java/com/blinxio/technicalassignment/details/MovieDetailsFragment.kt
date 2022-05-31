package com.blinxio.technicalassignment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.R
import com.blinxio.technicalassignment.databinding.FragmentMovieDetailsBinding
import com.blinxio.technicalassignment.details.models.MovieDetails
import com.blinxio.technicalassignment.details.viewmodel.MovieDetailsViewModel
import com.blinxio.technicalassignment.network.ApiResult
import com.blinxio.technicalassignment.utils.repeatOnLifecycleStarted
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var binding: FragmentMovieDetailsBinding? = null
    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()
    private var snack: Snackbar? = null

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
        repeatOnLifecycleStarted {
            movieDetailsViewModel.movieDetails.collect { apiResult ->
                binding?.loadingProgressBar?.isVisible = apiResult is ApiResult.Loading
                when (apiResult) {
                    is ApiResult.Success -> {
                        populateViewsWhenSuccess(apiResult)
                    }
                    is ApiResult.Error -> {
                        showErrorSnack(view)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun showErrorSnack(view: View) {
        snack = Snackbar.make(
            view, R.string.error_occured_message, Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.error_retry_option) { movieDetailsViewModel.getMovieDetails() }
        snack!!.show()
    }

    private fun populateViewsWhenSuccess(apiResult: ApiResult.Success<MovieDetails>) {
        val movieDetails = apiResult.value
        binding?.apply {
            movieDetailsBackdrop.load("${BuildConfig.IMAGE_URL}/${BuildConfig.IMAGE_SIZE}/${movieDetails.backdropPath}")
            movieDetailsPoster.load("${BuildConfig.IMAGE_URL}/${BuildConfig.IMAGE_SIZE}/${movieDetails.posterPath}")
            movieDetailsSummary.text = movieDetails.overview
            movieDetailsTitle.text = movieDetails.title
            movieDetailsYear.text = movieDetails.releaseDate
            movieDetailsTagline.text = movieDetails.tagline
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        snack?.dismiss()
        binding = null
        snack = null
    }
}