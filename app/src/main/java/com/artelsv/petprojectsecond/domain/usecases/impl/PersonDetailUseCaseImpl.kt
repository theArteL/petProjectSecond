package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.usecases.PersonDetailUseCase
import io.reactivex.Single
import javax.inject.Inject

class PersonDetailUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
): PersonDetailUseCase {
    override fun getMovies(personId: Int): Single<List<Movie>> {
        return moviesRepository.getMoviesByCredits(personId)
    }
}