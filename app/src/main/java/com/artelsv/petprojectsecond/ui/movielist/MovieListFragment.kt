package com.artelsv.petprojectsecond.ui.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentMovieListBinding
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var router: Router

    private val binding: FragmentMovieListBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val nowPlayingAdapter: MovieAdapter = MovieAdapter {
        it?.let {
            router.navigateTo(Screens.movieDetail(it.id))
        }
    }

//    private val popularAdapter: MovieAdapter = MovieAdapter {
//        router.navigateTo(Screens.movieDetail(it.id))
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMoviesNowPlayingRv()
        setMoviesPopularRv()
        setSortPopup()
        setListeners()
        setObservers()
    }

    private fun setSortPopup() {
        val popupMenu = PopupMenu(requireContext(), binding.tvSort)
        popupMenu.inflate(R.menu.menu_sort)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_no -> {
                    viewModel.getMovies(MovieSortType.NO)
                    true
                }
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
    }

    private fun setListeners() {
        binding.btnError.setOnClickListener {
            viewModel.getMovies()
        }
    }

    private fun setObservers() {
        viewModel.popularMovies.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
//                popularAdapter.submitData(it)
//                popularAdapter.data = it
            }
        })

        val dis = viewModel.pagingData.subscribe {
            nowPlayingAdapter.submitData(lifecycle, it)
        }

//        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
//            if (!it.isNullOrEmpty()) {
//                nowPlayingAdapter.data = it
//            }
//        })
    }

    private fun setMoviesNowPlayingRv() {
        binding.rvMoviesNowPlaying.apply {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
            addItemDecoration(HorizontalMarginItemDecoration(requireContext().resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt(), horizontal = false))
        }
    }

    private fun setMoviesPopularRv() {
        binding.rvMoviesPopular.apply {
//            adapter = popularAdapter
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            addItemDecoration(HorizontalMarginItemDecoration(requireContext().resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt()))
        }
    }
}