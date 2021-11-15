package com.artelsv.petprojectsecond.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artelsv.petprojectsecond.databinding.ItemUserListBinding
import com.artelsv.petprojectsecond.domain.model.movie.MovieList

class UserListAdapter(
    private val clickListener: (clickData: Pair<MovieList, Int>) -> Unit
) : ListAdapter<Pair<MovieList, Int>, UserListAdapter.UserListViewHolder>(UserListDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserListViewHolder {
        return UserListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class UserListViewHolder private constructor(
        private val binding: ItemUserListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Pair<MovieList, Int>,
            onClickListener: (clickData: Pair<MovieList, Int>) -> Unit
        ) {
            binding.item = item.first
            binding.type = binding.root.resources.getString(item.second)

            binding.mcvContainer.setOnClickListener {
                onClickListener(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): UserListViewHolder {
                val binding = ItemUserListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )

                return UserListViewHolder(binding)
            }
        }
    }

    private object UserListDiffItemCallback : DiffUtil.ItemCallback<Pair<MovieList, Int>>() {

        override fun areItemsTheSame(oldItem: Pair<MovieList, Int>, newItem: Pair<MovieList, Int>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<MovieList, Int>, newItem: Pair<MovieList, Int>): Boolean {
            return oldItem.first == newItem.first
        }
    }
}