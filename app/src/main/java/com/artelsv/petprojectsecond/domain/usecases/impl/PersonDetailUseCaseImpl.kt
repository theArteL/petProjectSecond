package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import com.artelsv.petprojectsecond.domain.usecases.PersonDetailUseCase
import io.reactivex.Single
import javax.inject.Inject

class PersonDetailUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
): PersonDetailUseCase {
    override fun getDetail(personId: Int): Single<PersonDetail> {
        return moviesRepository.getPersonDetail(personId)
    }

    override fun getMovies(personId: Int): Single<List<Movie>> {
        return moviesRepository.getMoviesByCredits(personId)
    }
}