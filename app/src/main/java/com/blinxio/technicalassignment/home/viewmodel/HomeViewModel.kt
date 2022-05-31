package com.blinxio.technicalassignment.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinxio.technicalassignment.home.models.MovieModel
import com.blinxio.technicalassignment.network.ApiResult
import com.blinxio.technicalassignment.network.MoviesApiInterface
import com.blinxio.technicalassignment.network.unwrapResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesApiInterface: MoviesApiInterface
) : ViewModel() {

    private val _moviesList = MutableStateFlow<ApiResult<MovieModel?>>(ApiResult.Loading)
    val moviesList = _moviesList.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _moviesList.value =
                try {
                    moviesApiInterface.getListOfMovies().unwrapResponse()
                } catch (e: Exception) {
                    ApiResult.Error(e)
                }
        }
    }
}