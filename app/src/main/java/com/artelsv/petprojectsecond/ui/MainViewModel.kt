package com.artelsv.petprojectsecond.ui

import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): BaseViewModel() {

}