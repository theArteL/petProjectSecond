package com.artelsv.petprojectsecond.ui.movie_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.artelsv.petprojectsecond.databinding.FragmentMovieDetailBinding
import com.artelsv.petprojectsecond.di.factory.ViewModelFactory
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.ui.movie_list.MovieListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        arguments?.let {
            viewModel.setMovieValue(it[Movie::class.simpleName] as Movie)
        }

        setObservers(binding)

        return binding.root
    }

    private fun setObservers(binding: FragmentMovieDetailBinding) {
        viewModel.movie.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.ivPoster.load(viewModel.getImageUrl(it))
            }
        })
    }

    companion object {
        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Movie::class.simpleName, movie)
            }
        }
    }
}