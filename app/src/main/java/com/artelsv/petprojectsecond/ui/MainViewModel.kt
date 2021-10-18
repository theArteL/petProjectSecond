package com.artelsv.petprojectsecond.ui

import com.artelsv.petprojectsecond.domain.UserRepository
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): BaseViewModel() {

}