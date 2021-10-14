package com.artelsv.petprojectsecond.ui

import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.usecases.AuthAsGuestUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authAsGuestUseCase: AuthAsGuestUseCase,
    private val preferenceManager: SharedPreferenceManager
) : BaseViewModel() {
    val error = MutableLiveData<String>(null)

    val session = MutableLiveData(false)
    val guestSession = MutableLiveData(false)

    fun authAsGuest() {
        compositeDisposable.add(
            authAsGuestUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferenceManager.addGuestSession(it)

                    guestSession.postValue(preferenceManager.getSession() != null)
                }, {
                    error.postValue(it.localizedMessage)
                })
        )
    }
}