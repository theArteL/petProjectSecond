package com.artelsv.petprojectsecond.domain.usecases.impl.movies

import com.artelsv.petprojectsecond.domain.repository.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.usecases.movies.GetMovieDateReleaseUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDateReleaseUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDateReleaseUseCase {
    override fun invoke(movieId: Int, iso: String): Single<DateReleaseResult> = moviesRepository.getMovieDateRelease(movieId).map {
        getMovieDateReleaseModified(it, iso)
    }

    private fun getMovieDateReleaseModified(data: List<DateReleaseResult>, iso: String) : DateReleaseResult =
        data.firstOrNull { filter -> filter.iso == iso } ?:
            data.firstOrNull { filter -> filter.iso == DEFAULT_ISO } ?:
                data.first()


    companion object {
        private const val DEFAULT_ISO = "US"
    }
}