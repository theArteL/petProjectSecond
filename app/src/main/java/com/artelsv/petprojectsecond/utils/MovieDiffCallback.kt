package com.artelsv.petprojectsecond.utils

import androidx.recyclerview.widget.DiffUtil
import com.artelsv.petprojectsecond.domain.model.Movie

open class MovieDiffCallback(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].originalTitle == newList[newItemPosition].originalTitle &&
                oldList[oldItemPosition].originalLanguage == newList[newItemPosition].originalTitle &&
                oldList[oldItemPosition].budget == newList[newItemPosition].budget &&
                oldList[oldItemPosition].overview == newList[newItemPosition].overview
    }
}
