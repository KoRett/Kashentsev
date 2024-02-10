package com.korett.kashentsev.presentation.popular.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.kashentsev.databinding.MovieItemBinding
import com.korett.kashentsev.domain.model.MoviePreview

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: MoviePreview, onItemClick: ((movieId: Int) -> Unit)) {
        binding.root.setOnClickListener {
            onItemClick(item.movieId)
        }
        Glide.with(binding.root.context)
            .load(item.posterUrlPreview)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imPoster)
        binding.textTitle.text = item.title
        binding.textInfo.text = "${item.genres[0]} (${item.year})".replaceFirstChar(Char::titlecase)
    }

    companion object {
        fun create(view: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = MovieItemBinding.inflate(inflater, view, false)
            return MovieViewHolder(binding)
        }
    }
}