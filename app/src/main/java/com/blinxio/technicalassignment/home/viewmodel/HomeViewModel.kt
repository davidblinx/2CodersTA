package com.blinxio.technicalassignment.home.viewmodel

import androidx.lifecycle.ViewModel
import com.blinxio.technicalassignment.network.MoviesApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesApiInterface: MoviesApiInterface
) : ViewModel() {
    
}