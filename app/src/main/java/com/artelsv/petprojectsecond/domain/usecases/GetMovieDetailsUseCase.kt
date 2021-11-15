package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import io.reactivex.Single

interface GetMovieDetailsUseCase {
    operator fun invoke(movieId: Int): Single<MovieDetail>

    fun getMovieCredits(movieId: Int): Single<Credits>
    fun rate(movieId: Int, rating: Number): Single<Boolean>
    fun favorite(accountId: Int, movieId: Int, favorite: Boolean): Single<Boolean>
}