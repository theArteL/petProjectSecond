package com.artelsv.petprojectsecond.ui.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.usecases.GetUserUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {
    val loading = MutableLiveData(true)
    val error = MutableLiveData<Throwable>(null)

    val user = MutableLiveData<User>(null)
    val saveUri = MutableLiveData<Uri>(null)

    init {
        compositeDisposable.add(getUserUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                user.postValue(it)
                loading.postValue(false)
            }
            .subscribe({

            }, {
                error.postValue(it)
            })
        )
    }

    fun exit() {
        getUserUseCase.exit()
    }
}