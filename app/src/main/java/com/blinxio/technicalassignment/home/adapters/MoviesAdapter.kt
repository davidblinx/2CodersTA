package com.blinxio.technicalassignment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blinxio.technicalassignment.databinding.ItemHolderMoviesBinding
import com.blinxio.technicalassignment.home.models.MovieResult
import com.blinxio.technicalassignment.home.viewholders.MovieViewHolder
import javax.inject.Inject

class MoviesAdapter @Inject constructor() :
    ListAdapter<MovieResult, MovieViewHolder>(DiffUtilCallback()) {

    var onClickListener: ((MovieResult) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemHolderMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MovieViewHolder(binding)
        holder.itemView.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onClickListener?.invoke(getItem(position))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffUtilCallback : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
        oldItem == newItem
}