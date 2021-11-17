package com.artelsv.petprojectsecond.domain.usecases.impl.movies.detail

import com.artelsv.petprojectsecond.domain.model.movie.ToggleFavorite
import com.artelsv.petprojectsecond.domain.repository.UserMoviesRepository
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.ToggleFavoriteMovieUseCase
import io.reactivex.Single
import javax.inject.Inject

class ToggleFavoriteMovieUseCaseImpl @Inject constructor(
    private val userMoviesRepository: UserMoviesRepository,
) : ToggleFavoriteMovieUseCase{

    override fun invoke(accountId: Int, movieId: Int, favorite: Boolean): Single<Boolean> {
        return userMoviesRepository.toggleFavorite(
            ToggleFavorite("movie", movieId, favorite),
            accountId
        )
    }
}