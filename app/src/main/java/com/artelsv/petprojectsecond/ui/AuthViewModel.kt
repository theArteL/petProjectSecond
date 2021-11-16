package com.artelsv.petprojectsecond.ui

import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.usecases.*
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthAsGuestUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.CreateSessionUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.GetRequestTokenUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.utils.exts.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authAsGuestUseCase: AuthAsGuestUseCase,
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val authUserUseCase: AuthUserUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val userUseCase: GetUserUseCase,
) : BaseViewModel() {
    val loading = MutableLiveData(true)
    val auth = SingleLiveEvent<Boolean>()

    val login = MutableLiveData("")
    val password = MutableLiveData("")

    val loginError = MutableLiveData("")
    val passwordError = MutableLiveData("")

    val error = MutableLiveData<String>(null)

    val requestToken = MutableLiveData("")
    val session = MutableLiveData(false)
    val guestSession = MutableLiveData(false)

    init {
        getRequestToken()
        checkAuth()
    }

    private fun checkAuth() {
        userUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                auth.postValue(true)
            }
            .subscribe({

            }, {
                loading.postValue(false)
//                error.postValue(it.localizedMessage)
                Timber.tag("auth").i(it.localizedMessage)
            }).addToComposite()
    }

    private fun getRequestToken() {
        getRequestTokenUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { requestToken.postValue(it) }
            .subscribe({

            }, {

            }).addToComposite()
    }

    fun authAsGuest() {
        authAsGuestUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                guestSession.postValue(true)
            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    private fun createSession(requestToken: String) {
        createSessionUseCase(requestToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                session.postValue(true)
            }
            .subscribe({

            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    fun authAsUser() {
        if (!validateAuth(login.value, password.value)) return

        val requestToken = requestToken.value ?: return

        authUserUseCase(requestToken,
            login.value!!,
            password.value!!) // использую тут !! из-за проверки выше (по идеи не могут быть пустыми или null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                createSession(it)
            }
            .subscribe({

            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    private fun validateAuth(login: String?, password: String?): Boolean {
        if (!(!login.isNullOrEmpty() && login.length > LOGIN_SIZE - 1)) loginError.postValue(
            LOGIN_ERROR)
        if (!(!password.isNullOrEmpty() && password.length > LOGIN_SIZE - 1)) passwordError.postValue(
            PASSWORD_ERROR)
        return (!login.isNullOrEmpty() && login.length > LOGIN_SIZE - 1) && (!password.isNullOrEmpty() && password.length > PASSWORD_SIZE - 1)
    }

    companion object {
        const val LOGIN_SIZE = 6
        const val PASSWORD_SIZE = 6
        const val LOGIN_ERROR = "Минимум 6 символов"
        const val PASSWORD_ERROR = "Минимум 6 символов"
    }
}