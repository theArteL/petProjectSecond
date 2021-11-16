package com.artelsv.petprojectsecond.domain.usecases.user.usermovies

import com.artelsv.petprojectsecond.domain.model.movie.MovieList
import io.reactivex.Single

interface GetFavoriteMoviesUseCase {

    operator fun invoke(accountId: Int) : Single<MovieList>
}