package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.MovieList
import io.reactivex.Single

interface UserListsUseCase {

    fun getFavoriteMovies() : Single<MovieList>
    fun getFavoriteTvShows() : Single<MovieList>
    fun getRatedMovies() : Single<MovieList>
    fun getRatedTvShows() : Single<MovieList>
}