package com.artelsv.petprojectsecond.ui.persondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentPersonDetailBinding
import com.artelsv.petprojectsecond.domain.model.movie.credits.Cast
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PersonDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: PersonDetailViewModel

    private val binding: FragmentPersonDetailBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val moviesAdapter = MovieStaticAdapter {

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

        arguments?.let { bundle ->
            bundle[PERSON_VALUE]?.let {
                when (it) {
                    is Cast -> viewModel.setCastValue(it)
                    is Crew -> viewModel.setCrewValue(it)
                    else -> viewModel.processErrorWithAny()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
        setLists()
    }

    private fun loadAvatar(profilePath: String?, gender: Int?) {
        binding.ivPersonImage.load("https://image.tmdb.org/t/p/w500${profilePath}") {
            error(when (gender) {
                0 -> R.drawable.ic_empty_man
                1 -> R.drawable.ic_empty_women
                else -> R.drawable.ic_empty_man
            })
        }
    }

    private fun setLists() {
        binding.rvMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = moviesAdapter
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
        binding.ivBack.setOnClickListener {
            viewModel.navigationBack()
        }

        binding.ivDetailExpanded.setOnClickListener {
            viewModel.toggleDetailExpanded()
        }
    }

    private fun setObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            it?.let {
                moviesAdapter.data = it.sortedByDescending { movie -> movie.voteAverage }
            }
        })

        viewModel.cast.observe(viewLifecycleOwner, {
            it?.let {
                loadAvatar(it.profilePath, it.gender)
            }
        })

        viewModel.crew.observe(viewLifecycleOwner, {
            it?.let {
                loadAvatar(it.profilePath, it.gender)
            }
        })

        viewModel.detailIsOpen.observe(viewLifecycleOwner, {
            it?.let {
                processDetailExpanding(it)
            }
        })

        viewModel.personDetail.observe(viewLifecycleOwner, {
            it?.let {

            }
        })
    }

    private fun processDetailExpanding(value: Boolean) {
        val openAnimation = RotateAnimation(
            MIN_DEGREES,
            MAX_DEGREES,
            RotateAnimation.RELATIVE_TO_SELF, pivotX,
            RotateAnimation.RELATIVE_TO_SELF, pivotY
        ).apply {
            duration = EXPANDED_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            fillAfter = true
        }

        val closeAnimation = RotateAnimation(
            MAX_DEGREES,
            MIN_DEGREES,
            RotateAnimation.RELATIVE_TO_SELF, pivotX,
            RotateAnimation.RELATIVE_TO_SELF, pivotY
        ).apply {
            duration = EXPANDED_ANIMATION_DURATION
            interpolator = LinearInterpolator()
            fillAfter = true
        }

        binding.ivDetailExpanded.startAnimation(if (value) openAnimation else closeAnimation)
    }

    companion object {
        private const val EXPANDED_ANIMATION_DURATION = 300L
        private const val MIN_DEGREES = 0f
        private const val MAX_DEGREES = 180f
        private const val pivotX = 0.5f
        private const val pivotY = 0.5f

        private const val PERSON_VALUE = "personValue"

        fun newInstance(person: Any) = PersonDetailFragment().apply {
            arguments = bundleOf(
                PERSON_VALUE to person
            )
        }
    }
}