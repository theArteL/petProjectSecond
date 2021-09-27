package com.artelsv.petprojectsecond.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentMovieListBinding
import com.artelsv.petprojectsecond.di.factory.ViewModelFactory
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var router: Router

    private val binding: FragmentMovieListBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setObservers()
        setMoviesNowPlayingRv()
        setMoviesPopularRv()
        setListeners()

        val popupMenu = PopupMenu(requireContext(), binding.tvSort)
        popupMenu.inflate(R.menu.menu_sort)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_asc -> {
                    viewModel.getMovies(MovieSortType.ASC)
                    true
                }
                R.id.menu_desc -> {
                    viewModel.getMovies(MovieSortType.DESC)
                    true
                }
                else -> false
            }
        }

        binding.tvSort.setOnClickListener {
            popupMenu.show()
        }

        return binding.root
    }

    private fun setListeners() {
        binding.btnError.setOnClickListener {
            viewModel.getMovies()
        }
    }

    private fun setObservers() {
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

    private fun setMoviesNowPlayingRv() {
        binding.rvMoviesNowPlaying.adapter = MovieAdapter {
            router.navigateTo(Screens.movieDetail(it.id))
        }

        binding.rvMoviesNowPlaying.layoutManager = LinearLayoutManager(requireContext())

        binding.rvMoviesNowPlaying.addItemDecoration(HorizontalMarginItemDecoration(requireContext().resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt(), horizontal = false))
    }

    private fun setMoviesPopularRv() {
        binding.rvMoviesPopular.adapter = MovieAdapter {
            router.navigateTo(Screens.movieDetail(it.id))
        }

        binding.rvMoviesPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvMoviesPopular.addItemDecoration(HorizontalMarginItemDecoration(requireContext().resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt()))
    }
}