package com.korett.kashentsev.presentation.popular.info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.korett.kashentsev.app.App
import com.korett.kashentsev.databinding.FragmentMovieInfoBinding
import com.korett.kashentsev.domain.model.ErrorResultModel
import com.korett.kashentsev.domain.model.Movie
import com.korett.kashentsev.domain.model.PendingResultModel
import com.korett.kashentsev.domain.model.SuccessResultModel
import javax.inject.Inject
import javax.inject.Provider


class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: Provider<MovieInfoViewModel.Factory>
    private val vm: MovieInfoViewModel by viewModels { vmFactory.get() }

    private val args by navArgs<MovieInfoFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getMovie(args.movieId)

        vm.movieResult.observe(viewLifecycleOwner) { result ->
            binding.root.children.forEach { it.visibility = View.GONE }
            when (result) {
                is SuccessResultModel -> loadSuccessResult(result.data)
                is ErrorResultModel -> loadErrorResult()
                is PendingResultModel -> loadPendingResult()
                else -> {}
            }
        }

        binding.btRetry.setOnClickListener {
            vm.getMovie(args.movieId)
        }
    }

    private fun loadSuccessResult(movie: Movie) {
        Glide.with(binding.root.context)
            .load(movie.posterUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imPoster)
        binding.textTitle.text = movie.title
        binding.textInfo.text = movie.description
        binding.textGenresAll.text = movie.genres
        binding.textCountriesAll.text = movie.countries
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