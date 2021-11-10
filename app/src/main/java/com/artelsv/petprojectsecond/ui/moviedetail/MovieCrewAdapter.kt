package com.artelsv.petprojectsecond.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artelsv.petprojectsecond.databinding.ItemPersonBinding
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew

class MovieCrewAdapter(
    private val clickListener: (clickData: Crew) -> Unit,
) : ListAdapter<Crew, MovieCrewAdapter.ViewHolder>(CrewDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(
        private val binding: ItemPersonBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Crew,
            onClickListener: (clickData: Crew) -> Unit,
        ) {
            binding.ivPerson.load("https://image.tmdb.org/t/p/w500${item.profilePath}")

            binding.mcvContainer.setOnClickListener {
                onClickListener(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemPersonBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return ViewHolder(binding)
            }
        }
    }
}

private object CrewDiffItemCallback : DiffUtil.ItemCallback<Crew>() {

    override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
        return oldItem == newItem
    }
}