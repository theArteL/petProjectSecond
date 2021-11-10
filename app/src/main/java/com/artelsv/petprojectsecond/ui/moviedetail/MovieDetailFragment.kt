package com.artelsv.petprojectsecond.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.databinding.FragmentMovieDetailBinding
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

    private val castAdapter = MovieCastAdapter {

    }

    private val crewAdapter = MovieCrewAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.getMovieDetail(it[MOVIE_ID] as Int)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
        setLists()
    }

    private fun setLists() {
        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = castAdapter
        }

        binding.rvCast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = crewAdapter
        }
    }

    private fun setListeners() {
        binding.rbRate.setOnRatingBarChangeListener { _, fl, _ ->
            viewModel.rateMovie(fl)
        }
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireContext(), ERROR, Toast.LENGTH_LONG).show()
                router.backTo(Screens.movieList())
            }
        })

        viewModel.credits.observe(viewLifecycleOwner, {
            it?.let {
                (binding.rvCast.adapter as MovieCastAdapter).submitList(it.cast)
                (binding.rvCrew.adapter as MovieCrewAdapter).submitList(it.crew)
            }
        })
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        private const val ERROR = "Ошибка, повторите попытку" // пока тут

        fun newInstance(movieId: Int) = MovieDetailFragment().apply {
            arguments = bundleOf(
                MOVIE_ID to movieId
            )
        }
    }
}
