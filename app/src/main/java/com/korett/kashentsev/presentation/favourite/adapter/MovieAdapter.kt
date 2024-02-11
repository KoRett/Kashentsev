package com.korett.kashentsev.presentation.favourite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.korett.kashentsev.domain.model.MoviePreview

class MovieAdapter(private val dataSet: List<MoviePreview>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

}