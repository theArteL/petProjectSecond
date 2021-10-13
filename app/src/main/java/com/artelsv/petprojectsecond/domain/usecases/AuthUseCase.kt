package com.artelsv.petprojectsecond.domain.usecases

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface AuthUseCase {
    operator fun invoke(): Single<String>
}