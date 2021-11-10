package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.MovieListResponse
import com.artelsv.petprojectsecond.domain.model.movie.MovieList

object MovieListMapper {

    fun movieListResponseToMovieList(value: MovieListResponse) =
        MovieList(value.page, value.results.map(MovieMapper::toMovie), value.totalPages, value.totalResults)
}