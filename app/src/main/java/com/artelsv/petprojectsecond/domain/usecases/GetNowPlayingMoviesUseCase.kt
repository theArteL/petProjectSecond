package com.artelsv.petprojectsecond.domain.usecases

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.domain.model.MovieSortType
import io.reactivex.Flowable

interface GetNowPlayingMoviesUseCase {
    operator fun invoke(sortType: MovieSortType): Flowable<PagingData<Movie>>
}