package com.artelsv.petprojectsecond.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ItemMovieBinding
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.utils.Constants.BASE_IMAGE_URL
import com.artelsv.petprojectsecond.utils.MovieDiffCallback

class MovieAdapter(
    private val clickListener: (clickData: Movie?) -> Unit
) : PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffItemCallback) {

//    var data = listOf<Movie>()
//        set(value) {
//            val diffCallback = MovieDiffCallback(field, value)
//            val diffResult = DiffUtil.calculateDiff(diffCallback)
//            diffResult.dispatchUpdatesTo(this)
//
//            field = value
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]

        holder.bind(getItem(position), clickListener)
    }

    // у меня слов нету, я так долго сидел и не мог понять шо не так. А я всего лишь забыл удалить строчку ниже. Мда... Оставлю на память
//    override fun getItemCount() = data.size

    class ViewHolder private constructor(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Movie?,
            onClickListener: (clickData: Movie?) -> Unit
        ) {
            binding.item = item

            binding.tvVote.text = getVoteAsString(item)
            binding.ivBackground.load(getImageUrl(item))
            binding.tvVote.setTextColor(binding.root.resources.getColor(getVoteColor(item), binding.root.resources.newTheme()))

            binding.mcvMovie.setOnClickListener {
                onClickListener(item)
            }
        }

        private fun getImageUrl(item: Movie?) = BASE_IMAGE_URL + item?.backdropPath

        private fun getVoteAsString(item: Movie?) = item?.voteAverage.toString()

        private fun getVoteColor(item: Movie?) = when(item?.voteAverage ?: 0.0) {
            in 0.0..5.0 -> R.color.red
            in 5.1..7.0 -> R.color.yellow
            in 7.1..10.0 -> R.color.green
            else -> R.color.red
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return ViewHolder(binding)
            }
        }
    }
}

private object MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }
}