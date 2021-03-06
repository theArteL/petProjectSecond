package com.artelsv.petprojectsecond.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.FragmentMovieListBinding
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import com.github.terrakok.cicerone.Router
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
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

    private val popularAdapter: MovieAdapter = MovieAdapter {
        it?.let {
            router.navigateTo(Screens.movieDetail(it.id))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
//        val popupMenu = PopupMenu(requireContext(), binding.tvSort)
//        popupMenu.inflate(R.menu.menu_sort)
//        popupMenu.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.menu_no -> {
//                    viewModel.getNowPlayingMovies(MovieSortType.NO)
//                    true
//                }
//                R.id.menu_asc -> {
//                    viewModel.getNowPlayingMovies(MovieSortType.ASC)
//                    true
//                }
//                R.id.menu_desc -> {
//                    viewModel.getNowPlayingMovies(MovieSortType.DESC)
//                    true
//                }
//                else -> false
//            }
//        }
//
//        binding.tvSort.setOnClickListener {
//            popupMenu.show()
//        }
    }

    private fun setListeners() {
//        binding.btnError.setOnClickListener {
//            viewModel.getNowPlayingMovies(MovieSortType.NO)
//        }

        binding.tvUser.setOnClickListener {
            router.navigateTo(Screens.profile())
        }
    }

    private fun setObservers() {
        viewModel.nowPlayingPagingLiveData.observe(viewLifecycleOwner, {
            it?.let {
                nowPlayingAdapter.submitData(lifecycle, it)
            }
        })

        viewModel.popularPagingLiveData.observe(viewLifecycleOwner, {
            it?.let {
                popularAdapter.submitData(lifecycle, it)
            }
        })

        viewModel.loadingNowPlaying.observe(viewLifecycleOwner, {
            if (viewModel.progressCheck()) loadDone()
        })

        viewModel.loadingPopular.observe(viewLifecycleOwner, {
            if (viewModel.progressCheck()) loadDone()
        })
    }

    private fun loadDone() {
        if (binding.pbLoading.isVisible) binding.pbLoading.visibility = View.GONE
        if (!binding.clMain.isVisible) binding.clMain.visibility = View.VISIBLE
    }

    private fun setMoviesNowPlayingRv() {
        binding.rvMoviesNowPlaying.apply {
            nowPlayingAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoaderStateAdapter(),
                footer = MovieLoaderStateAdapter()
            )

            nowPlayingAdapter.addLoadStateListener { state ->
                viewModel.loadingNowPlaying.postValue(state.refresh != LoadState.Loading)
            }

            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    requireContext().resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt(), horizontal = false
                )
            )
        }
    }

    private fun setMoviesPopularRv() {
        binding.rvMoviesPopular.apply {
            popularAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoaderStateAdapter(),
                footer = MovieLoaderStateAdapter()
            )

            popularAdapter.addLoadStateListener { state ->
                viewModel.loadingPopular.postValue(state.refresh != LoadState.Loading)
            }

            adapter = popularAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    requireContext().resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt()
                )
            )
        }
    }
}