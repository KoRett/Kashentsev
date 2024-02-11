package com.korett.kashentsev.presentation.favourite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.kashentsev.app.App
import com.korett.kashentsev.databinding.FragmentFavouriteMoviesBinding
import com.korett.kashentsev.domain.model.ErrorResultModel
import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.MoviePreview
import com.korett.kashentsev.domain.model.PendingResultModel
import com.korett.kashentsev.domain.model.SuccessResultModel
import com.korett.kashentsev.presentation.favourite.adapter.MovieAdapter
import javax.inject.Inject
import javax.inject.Provider

class FavouriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: Provider<FavouriteMoviesViewModel.Factory>
    private val vm: FavouriteMoviesViewModel by viewModels { vmFactory.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteMoviesBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.movieResult.observe(viewLifecycleOwner) { result ->
            binding.root.children.forEach { it.visibility = View.GONE }
            when (result) {
                is SuccessResultModel -> loadSuccessResult(result.data)
                is ErrorResultModel -> loadErrorResult()
                is PendingResultModel -> loadPendingResult()
                else -> {}
            }
        }
        vm.getFavouriteMoviePreviews()
    }

    private fun loadSuccessResult(moviePreviews: List<MoviePreview>) {
        val adapter = MovieAdapter(moviePreviews)
        binding.moviesList.adapter = adapter
        binding.root.children.filter { it.id != binding.progressBar.id && it.id != binding.error.id }
            .forEach { it.visibility = View.VISIBLE }
    }

    private fun loadErrorResult() {
        binding.error.visibility = View.VISIBLE
    }

    private fun loadPendingResult() {
        binding.progressBar.visibility = View.VISIBLE
    }
}