package com.blinxio.technicalassignment.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinxio.technicalassignment.details.MovieDetailsFragmentArgs
import com.blinxio.technicalassignment.details.models.MovieDetails
import com.blinxio.technicalassignment.network.ApiResult
import com.blinxio.technicalassignment.network.MoviesApiInterface
import com.blinxio.technicalassignment.network.unwrapResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesApiInterface: MoviesApiInterface
) : ViewModel() {

    private val args = MovieDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _movieDetails = MutableStateFlow<ApiResult<MovieDetails>?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            _movieDetails.value = try {
                moviesApiInterface.getMovieDetails(args.movieId).unwrapResponse()
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        }
    }
}