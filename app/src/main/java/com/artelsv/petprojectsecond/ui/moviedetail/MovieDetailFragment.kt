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
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentMovieDetailBinding
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    private val binding: FragmentMovieDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val castAdapter = MovieCastAdapter {
        viewModel.navigateToPersonCast(it)
    }

    private val crewAdapter = MovieCrewAdapter {
        viewModel.navigateToPersonCrew(it)
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
            viewModel.getMovieDetail(it.getInt(MOVIE_ID))
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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    requireContext().resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt()
                )
            )
        }

        binding.rvCrew.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = crewAdapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    requireContext().resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt()
                )
            )
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
                viewModel.navigateBack()
            }
        })

        viewModel.credits.observe(viewLifecycleOwner, {
            it?.let {
                castAdapter.submitList(it.cast.filter { cast -> cast.knownForDepartment == "Acting" })
                crewAdapter.submitList(it.crew.filter { crew -> crew.knownForDepartment != "Acting" })
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
