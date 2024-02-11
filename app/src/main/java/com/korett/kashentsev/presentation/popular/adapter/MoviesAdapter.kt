package com.korett.kashentsev.presentation.popular.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.korett.kashentsev.domain.model.MoviePreview

class MoviesAdapter (private val onItemClick: ((movieId: Int) -> Unit), private val onItemLongClick: ((moviePreview: MoviePreview, isFavourite: Boolean) -> Unit)) : PagingDataAdapter <MoviePreview, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null)
            (holder as MovieViewHolder).bind(movieItem, onItemClick, onItemLongClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.create(parent)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MoviePreview>() {
            override fun areItemsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: MoviePreview, newItem: MoviePreview): Boolean =
                oldItem == newItem
        }
    }
}