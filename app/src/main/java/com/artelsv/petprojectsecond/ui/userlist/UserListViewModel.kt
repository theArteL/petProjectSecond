package com.artelsv.petprojectsecond.ui.userlist

import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): BaseViewModel() {
}