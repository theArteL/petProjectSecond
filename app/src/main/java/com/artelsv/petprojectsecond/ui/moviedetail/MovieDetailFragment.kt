package com.artelsv.petprojectsecond.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.artelsv.petprojectsecond.databinding.FragmentMovieDetailBinding
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.ui.Screens
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    @Inject
    lateinit var router: Router

    private val binding: FragmentMovieDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.setMovieValue(it[MOVIE_ID] as Int)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        viewModel.movie.observe(viewLifecycleOwner, {
            if (it != null) {
                setData(it)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, {
            if (it != null && it) {
                Toast.makeText(requireContext(), ERROR, Toast.LENGTH_LONG).show()
                router.backTo(Screens.movieList())
            }
        })
    }

    private fun setData(movie: MovieDetail) {

        binding.ivPoster.load(viewModel.getImageUrl(movie))

        binding.tvVote.text = viewModel.getVoteAsString(movie)
        binding.tvVote.setTextColor(
            binding.root.resources.getColor(
                viewModel.getVoteColor(movie),
                binding.root.resources.newTheme()
            )
        )

        binding.tvTitle.text = viewModel.getMovieName(resources)
        binding.tvGenres.text = viewModel.getGenresAsString(resources)
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        private const val ERROR = "Ошибка, повторите попытку" // пока тут

        fun newInstance(movieId: Int) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(MOVIE_ID, movieId)
            }
        }
    }
}