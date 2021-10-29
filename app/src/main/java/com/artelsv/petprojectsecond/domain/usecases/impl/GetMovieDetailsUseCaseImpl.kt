package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.model.RateMovie
import com.artelsv.petprojectsecond.domain.model.ToggleFavorite
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val userRepository: UserRepository
) : GetMovieDetailsUseCase {
    override fun invoke(movieId: Int): Single<MovieDetail> = moviesRepository.getMovieDetails(movieId)

    override fun rate(movieId: Int, rating: Number): Single<Boolean> {
        return userRepository.rateMovie(
            RateMovie(rating),
            movieId
        )
    }

    override fun favorite(movieId: Int, favorite: Boolean): Single<Boolean> {
        return userRepository.toggleFavorite(
            ToggleFavorite("", movieId, favorite),
            userRepository.getLocalUser()?.id ?: 0
        )
    }
}