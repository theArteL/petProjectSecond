package com.artelsv.petprojectsecond.domain.usecases

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDateReleaseUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDateReleaseUseCase {
    override fun invoke(movieId: Int, iso: String): Single<DateReleaseResult> = moviesRepository.getMovieDateRelease(movieId).map {
        getMovieDateRelease(it, iso)
    }

    private fun getMovieDateRelease(data: List<DateReleaseResult>, iso: String) : DateReleaseResult {
        var first = data.firstOrNull { filter -> filter.iso == iso }

        if (first == null) { // если ру нету, то берем US
            first = data.firstOrNull { filter -> filter.iso == DEFAULT_ISO }
        }

        if (first == null) { // если нету ру и юс, то берем первую
            first = data.first()
        }

        return first
    }

    companion object {
        private const val DEFAULT_ISO = "US"
    }
}