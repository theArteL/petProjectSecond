package com.artelsv.petprojectsecond.ui.persondetail.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.databinding.ItemMovieBinding
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.utils.Constants

class MovieStaticViewHolder private constructor(
    private val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Movie?,
        onClickListener: (clickData: Movie?) -> Unit,
    ) {
        binding.item = item

        binding.tvVote.text = getVoteAsString(item)
        binding.ivBackground.load(getImageUrl(item))
        binding.tvVote.setTextColor(
            binding.root.resources.getColor(
                getVoteColor(item),
                binding.root.resources.newTheme()
            )
        )

        binding.mcvMovie.setOnClickListener {
            onClickListener(item)
        }
    }

    private fun getImageUrl(item: Movie?) = Constants.BASE_IMAGE_URL + item?.backdropPath

    private fun getVoteAsString(item: Movie?) = item?.voteAverage.toString()

    private fun getVoteColor(item: Movie?) = when (item?.voteAverage ?: 0.0) {
        in 0.0..5.0 -> R.color.red
        in 5.1..7.0 -> R.color.yellow
        in 7.1..10.0 -> R.color.green
        else -> R.color.red
    }

    companion object {
        fun from(parent: ViewGroup): MovieStaticViewHolder {
            val binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

            return MovieStaticViewHolder(binding)
        }
    }
}