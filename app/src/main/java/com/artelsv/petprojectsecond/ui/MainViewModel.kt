package com.artelsv.petprojectsecond.ui

import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {

    fun navigateBack() {
        router.exit()
    }
}