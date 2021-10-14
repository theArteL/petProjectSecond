package com.artelsv.petprojectsecond.ui

import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.usecases.AuthAsGuestUseCase
import com.artelsv.petprojectsecond.domain.usecases.AuthUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetRequestTokenUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.utils.SharedPreferenceManager
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.log

class AuthViewModel @Inject constructor(
    private val authAsGuestUseCase: AuthAsGuestUseCase,
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val authUserUseCase: AuthUserUseCase,
    private val preferenceManager: SharedPreferenceManager
) : BaseViewModel() {
    val login = MutableLiveData("")
    val password = MutableLiveData("")

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

    private fun authAsUser(requestToken: String, login: String?, password: String?) {

        if (login.isNullOrEmpty()) return
        if (password.isNullOrEmpty()) return

        compositeDisposable.add(
            authUserUseCase.invoke(requestToken, login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    preferenceManager.addSession(it)
                    preferenceManager.addAuth(true)
                    session.postValue(preferenceManager.getSession() != null)
                }, {

                })
        )
    }

    fun createRequestTokenAndAuth() {
        compositeDisposable.add(
            getRequestTokenUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    authAsUser(it, login.value!!, password.value!!)
                }, {

                })
        )
    }
}