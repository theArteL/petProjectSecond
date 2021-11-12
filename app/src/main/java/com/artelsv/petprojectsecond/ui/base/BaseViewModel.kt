package com.artelsv.petprojectsecond.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()

        Log.e(this.toString(), "Cleared")
    }

    fun Disposable.addToComposite() {
        compositeDisposable.add(this)
    }
}