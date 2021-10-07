package com.artelsv.petprojectsecond.domain.usecases.impl

import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDateReleaseUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDateReleaseUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMovieDateReleaseUseCase {
    override fun invoke(movieId: Int, iso: String): Single<DateReleaseResult> = moviesRepository.getMovieDateRelease(movieId).map {
        getMovieDateReleaseModified(it, iso)
    }

    private fun getMovieDateRelease(data: List<DateReleaseResult>, iso: String) : DateReleaseResult { // старая версия тут пока
        var first = data.firstOrNull { filter -> filter.iso == iso }

        if (first == null) { // если ру нету, то берем US
            first = data.firstOrNull { filter -> filter.iso == DEFAULT_ISO }
        }

        if (first == null) { // если нету ру и юс, то берем первую
            first = data.first()
        }

        return first
    }

    // TODO: 07.10.2021 изучай возможности котлина и читай чужой код :)
    private fun getMovieDateReleaseModified(data: List<DateReleaseResult>, iso: String) : DateReleaseResult =
        data.firstOrNull { filter -> filter.iso == iso } ?:
            data.firstOrNull { filter -> filter.iso == DEFAULT_ISO } ?:
                data.first()


    companion object {
        private const val DEFAULT_ISO = "US"
    }
}