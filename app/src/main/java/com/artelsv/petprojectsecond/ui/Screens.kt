package com.artelsv.petprojectsecond.ui

import com.artelsv.petprojectsecond.ui.moviedetail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.movielist.MovieListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun movieList() = FragmentScreen { MovieListFragment() }
    fun movieDetail(movieId: Int) = FragmentScreen { MovieDetailFragment.newInstance(movieId) }
}