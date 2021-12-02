package com.artelsv.petprojectsecond.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import com.artelsv.petprojectsecond.ui.movielist.MovieAdapter
import com.artelsv.petprojectsecond.ui.profile.UserListAdapter
import com.artelsv.petprojectsecond.ui.utils.HorizontalMarginItemDecoration
import com.artelsv.petprojectsecond.utils.exts.safeLet

private const val IMAGE_URL = "https://image.tmdb.org/t/p/"
private const val IMAGE_WIDTH = "w200"

@BindingAdapter("avatarUrl")
fun bindAvatarUrlImage(view: ImageView?, url: String?) {
    safeLet(view, url) { _view, _url ->
        _view.load(IMAGE_URL + IMAGE_WIDTH + _url)
    }
}

@BindingAdapter("url")
fun bindUrlImage(view: ImageView?, url: String?) {
    view?.let {
        it.load(url)
    }
}

@BindingAdapter("adapter", "items", requireAll = true)
fun bindUserListAdapter(rv: RecyclerView?, adapter: UserListAdapter?, items: MutableLiveData<ArrayList<Pair<MovieList, Int>>>?) {
    safeLet(rv, adapter, items) { _rv, _adapter, _items ->
        _rv.adapter = adapter
        _adapter.submitList(_items.value?.toMutableList())
    }
}

@BindingAdapter("adapter", "items", requireAll = true)
fun bindMovieAdapter(rv: RecyclerView?, adapter: MovieAdapter?, items: LiveData<PagingData<Movie>?>, listener: (CombinedLoadStates) -> Unit) {
    safeLet(rv, adapter, items.value, { _rv, _adapter, _items ->
//        _adapter.addLoadStateListener { state ->
//            viewModel.loadingNowPlaying.postValue(state.refresh != LoadState.Loading)
//        }

        _adapter.addLoadStateListener(listener)

        adapter = nowPlayingAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            HorizontalMarginItemDecoration(
                requireContext().resources.getDimension(
                    R.dimen.viewpager_current_item_horizontal_margin
                ).toInt(), horizontal = false
            )
        )
    })
}