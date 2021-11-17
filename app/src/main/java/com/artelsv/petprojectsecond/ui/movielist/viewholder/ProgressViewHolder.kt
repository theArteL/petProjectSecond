package com.artelsv.petprojectsecond.ui.movielist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import com.artelsv.petprojectsecond.databinding.ItemProgressBinding
import com.artelsv.petprojectsecond.ui.movielist.MovieLoaderStateAdapter

class ProgressViewHolder internal constructor(
    binding: ItemProgressBinding
) : MovieLoaderStateAdapter.ItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) = Unit

    companion object {

        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToRoot: Boolean = false
        ): ProgressViewHolder {
            return ProgressViewHolder(
                ItemProgressBinding.inflate(
                    layoutInflater,
                    parent,
                    attachToRoot
                )
            )
        }
    }
}