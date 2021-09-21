package com.artelsv.petprojectsecond.ui

import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.ui.movie_detail.MovieDetailFragment
import com.artelsv.petprojectsecond.ui.movie_list.MovieListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun movieList() = FragmentScreen { MovieListFragment() }
    fun movieDetail(movie: Movie) = FragmentScreen { MovieDetailFragment.newInstance(movie) }
}