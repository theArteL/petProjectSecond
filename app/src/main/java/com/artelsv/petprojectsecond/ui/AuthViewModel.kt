package com.artelsv.petprojectsecond.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthAsGuestUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.AuthUserUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.CreateSessionUseCase
import com.artelsv.petprojectsecond.domain.usecases.auth.GetRequestTokenUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.utils.exts.SingleLiveEvent
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthViewModel @Inject constructor(
    private val authAsGuestUseCase: AuthAsGuestUseCase,
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val authUserUseCase: AuthUserUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val userUseCase: GetUserUseCase,
    private val router: Router,
) : BaseViewModel() {
    val loading = MutableLiveData(true)

    val login = MutableLiveData("")
    val password = MutableLiveData("")

    val loginError = MutableLiveData("")
    val passwordError = MutableLiveData("")

    val error = MutableLiveData<String>(null)

    val requestToken = MutableLiveData("")

    // для навигации
    val auth = SingleLiveEvent<Boolean>()
    val session = SingleLiveEvent<Boolean>()
    val guestSession = SingleLiveEvent<Boolean>()

    init {
        getRequestToken()
        checkAuth()
    }

    fun navigateBack() {
        router.exit()
    }

    fun navigateToMain(context: Context) {
        router.newRootScreen(Screens.mainActivity(context))
    }

    fun authAsGuest() {
        authAsGuestUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                guestSession.value = true
            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    fun authAsUser(context: Context) {
        if (!validateAuth(login.value, password.value, context)) return

        val requestToken = requestToken.value ?: return

        authUserUseCase(requestToken,
            login.value!!,
            password.value!!) // использую тут !! из-за проверки выше (по идеи не могут быть пустыми или null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                createSession(it)
            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    private fun checkAuth() {
        userUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                auth.postValue(true)
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
            .subscribe({
                requestToken.postValue(it)
            }, {

            }).addToComposite()
    }

    private fun createSession(requestToken: String) {
        createSessionUseCase(requestToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                session.value = true
            }, {
                error.postValue(it.localizedMessage)
            }).addToComposite()
    }

    private fun validateAuth(login: String?, password: String?, context: Context): Boolean {
        var isLoginOk = true
        var isPasswordOk = true

        if (login.isNullOrEmpty() || login.length < LOGIN_SIZE) {
            loginError.postValue(context.getString(LOGIN_ERROR))
            isLoginOk = false
        }

        if (password.isNullOrEmpty() || password.length < PASSWORD_SIZE) {
            passwordError.postValue(context.getString(PASSWORD_ERROR))
            isPasswordOk = false
        }

        return isLoginOk && isPasswordOk
    }

    companion object {
        private const val LOGIN_SIZE = 6
        private const val PASSWORD_SIZE = 6
        private const val LOGIN_ERROR = R.string.system_login_validate_error
        private const val PASSWORD_ERROR = R.string.system_password_validate_error
    }
}