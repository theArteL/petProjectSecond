package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.RateMovie
import com.artelsv.petprojectsecond.domain.model.movie.ToggleFavorite
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val userRepository: UserRepository,
) : GetMovieDetailsUseCase {
    override fun invoke(movieId: Int): Single<MovieDetail> =
        moviesRepository.getMovieDetails(movieId)

    override fun getMovieCredits(movieId: Int): Single<Credits> =
        moviesRepository.getMovieCredits(movieId)

    override fun rate(movieId: Int, rating: Number): Single<Boolean> {
        return userRepository.rateMovie(
            RateMovie(rating),
            movieId
        )
    }

    override fun favorite(accountId: Int, movieId: Int, favorite: Boolean): Single<Boolean> {
        return userRepository.toggleFavorite(
            ToggleFavorite("movie", movieId, favorite),
            accountId
        )
    }
}