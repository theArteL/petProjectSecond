package com.artelsv.petprojectsecond.domain.usecases

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import io.reactivex.Flowable

interface GetPopularMoviesUseCase {
    operator fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>>
}