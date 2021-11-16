package com.artelsv.petprojectsecond.domain.usecases.movies

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieSortType
import io.reactivex.Flowable

interface GetNowPlayingMoviesUseCase {
    operator fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>>
}