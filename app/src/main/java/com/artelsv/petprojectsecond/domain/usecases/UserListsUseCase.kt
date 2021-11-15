package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import io.reactivex.Single

interface UserListsUseCase {

    fun getFavoriteMovies(accountId: Int) : Single<MovieList>
    fun getFavoriteTvShows(accountId: Int) : Single<MovieList>
    fun getRatedMovies(accountId: Int) : Single<MovieList>
    fun getRatedTvShows(accountId: Int) : Single<MovieList>
}