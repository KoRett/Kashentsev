package com.korett.kashentsev.presentation.popular

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.korett.kashentsev.app.App
import com.korett.kashentsev.databinding.FragmentPopularMoviesBinding
import com.korett.kashentsev.presentation.popular.adapter.MoviesAdapter
import com.korett.kashentsev.presentation.popular.info.MovieInfoFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: Provider<PopularMoviesViewModel.Factory>
    private val vm: PopularMoviesViewModel by viewModels { vmFactory.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)

        val adapter = MoviesAdapter(onItemClick = { movieId ->
            val destination = PopularMoviesFragmentDirections.actionTopMoviesFragmentToMovieInfoFragment(movieId)
            findNavController().navigate(destination)
        })

        adapter.addLoadStateListener { loadState ->
            binding.moviesList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.error.isVisible = loadState.source.refresh is LoadState.Error
        }
        binding.moviesList.adapter = adapter

        lifecycleScope.launch {
            vm.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.btRetry.setOnClickListener {
            adapter.retry()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}