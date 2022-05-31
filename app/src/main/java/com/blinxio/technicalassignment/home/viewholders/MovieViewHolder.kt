package com.blinxio.technicalassignment.home.viewholders

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.blinxio.technicalassignment.BuildConfig
import com.blinxio.technicalassignment.databinding.ItemHolderMoviesBinding
import com.blinxio.technicalassignment.home.models.MovieResult

class MovieViewHolder(private val binding: ItemHolderMoviesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movieResult: MovieResult) {
        binding.movieCover.load("${BuildConfig.IMAGE_URL}/w500/${movieResult.posterPath}")
        binding.movieTitle.text = movieResult.title
        binding.movieDescription.text = movieResult.overview
    }
}