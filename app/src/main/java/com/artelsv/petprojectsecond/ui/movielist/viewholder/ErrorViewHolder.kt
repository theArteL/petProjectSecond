package com.artelsv.petprojectsecond.ui.movielist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import com.artelsv.petprojectsecond.databinding.ItemErrorBinding
import com.artelsv.petprojectsecond.ui.movielist.MovieLoaderStateAdapter

class ErrorViewHolder internal constructor(
    private val binding: ItemErrorBinding
) : MovieLoaderStateAdapter.ItemViewHolder(binding.root) {

    override fun bind(loadState: LoadState) {
        require(loadState is LoadState.Error)
        binding.errorMessage.text = loadState.error.localizedMessage
    }

    companion object {

        operator fun invoke(
            layoutInflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToRoot: Boolean = false
        ): ErrorViewHolder {
            return ErrorViewHolder(
                ItemErrorBinding.inflate(
                    layoutInflater,
                    parent,
                    attachToRoot
                )
            )
        }
    }
}