package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import io.reactivex.Single

interface PersonDetailUseCase {

    fun getDetail(personId: Int): Single<PersonDetail>
    fun getMovies(personId: Int): Single<List<Movie>>
}