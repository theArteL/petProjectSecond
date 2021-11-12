package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.movie.Movie
import io.reactivex.Single

interface PersonDetailUseCase {

    fun getMovies(personId: Int): Single<List<Movie>>
}