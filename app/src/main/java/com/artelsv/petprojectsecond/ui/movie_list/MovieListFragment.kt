package com.artelsv.petprojectsecond.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artelsv.petprojectsecond.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieListBinding.inflate(inflater)

        return binding.root
    }
}