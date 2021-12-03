package com.artelsv.petprojectsecond.ui

import android.view.View
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
import com.artelsv.petprojectsecond.ui.moviedetail.MovieCastAdapter
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

@BindingAdapter("adapterMovie")
fun bindMovieAdapter(rv: RecyclerView?, adapter: MovieAdapter?) {
    safeLet(rv, adapter, { _rv, _adapter ->
        _rv.apply {
            this.adapter = _adapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    _rv.context.resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt(), horizontal = false
                )
            )
        }
    })
}

@BindingAdapter("adapterPerson")
fun bindPersonAdapter(rv: RecyclerView?, adapter: MovieCastAdapter?) {
    safeLet(rv, adapter, { _rv, _adapter ->
        _rv.apply {
            this.adapter = _adapter
            addItemDecoration(
                HorizontalMarginItemDecoration(
                    _rv.context.resources.getDimension(
                        R.dimen.viewpager_current_item_horizontal_margin
                    ).toInt(), horizontal = false
                )
            )
        }
    })
}