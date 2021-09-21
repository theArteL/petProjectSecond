package com.artelsv.petprojectsecond.ui.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.artelsv.petprojectsecond.App
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentMovieListBinding
import com.artelsv.petprojectsecond.di.factory.ViewModelFactory
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.movie_detail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setObservers(binding)
        setMoviesNowPlayingRv(binding)
        setMoviesPopularRv(binding)

        return binding.root
    }

    private fun setObservers(binding: FragmentMovieListBinding) {
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                (binding.rvMoviesPopular.adapter as MovieAdapter).data = it
            }
        })

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                (binding.rvMoviesNowPlaying.adapter as MovieAdapter).data = it
            }
        })
    }

    private fun setMoviesNowPlayingRv(binding: FragmentMovieListBinding) {
        binding.rvMoviesNowPlaying.adapter = MovieAdapter {
            (requireActivity().application as App).getRouter().navigateTo(Screens.movieDetail(it))
        }

        binding.rvMoviesNowPlaying.layoutManager = LinearLayoutManager(requireContext())

        binding.rvMoviesNowPlaying.addItemDecoration(
            HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin,
                horizontal = false)
        )
    }

    private fun setMoviesPopularRv(binding: FragmentMovieListBinding) {
        binding.rvMoviesPopular.adapter = MovieAdapter {
            (requireActivity().application as App).getRouter().navigateTo(Screens.movieDetail(it))
        }

        binding.rvMoviesPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvMoviesPopular.addItemDecoration(
            HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin)
        )
    }
}