package com.artelsv.petprojectsecond.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.artelsv.petprojectsecond.databinding.FragmentMovieDetailBinding
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.ui.Screens
import com.github.terrakok.cicerone.Router
import com.google.android.material.textview.MaterialTextView
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
            viewModel.getMovieDetail(it[MOVIE_ID] as Int)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireContext(), ERROR, Toast.LENGTH_LONG).show()
                router.backTo(Screens.movieList())
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
