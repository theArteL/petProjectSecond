package com.artelsv.petprojectsecond.ui.persondetail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.ui.persondetail.viewholder.MovieStaticViewHolder

class MovieStaticAdapter(
    private val clickListener: (clickData: Movie?) -> Unit,
) : RecyclerView.Adapter<MovieStaticViewHolder>() {

    var data = listOf<Movie>()
        set(value) {
            val diffCallback = MovieStaticDiffItemCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)

            field = value

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieStaticViewHolder {
        return MovieStaticViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieStaticViewHolder, position: Int) =
        holder.bind(data[position], clickListener)

    override fun getItemCount(): Int = data.size

    inner class MovieStaticDiffItemCallback(
        private val oldList: List<Movie>,
        private val newList: List<Movie>,
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
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}