package com.artelsv.petprojectsecond.ui.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.base.BaseViewModel
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.utils.Constants.BASE_IMAGE_URL
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
    ) : BaseViewModel() {
    private val mMovie = MutableLiveData<MovieDetail>(null)
    val movie: LiveData<MovieDetail> = mMovie

    val loading = MutableLiveData(true)
    val error = MutableLiveData(false)

    fun setMovieValue(movieId: Int) {
        val dis = getMovieDetailsUseCase.invoke(movieId).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({
            mMovie.postValue(it)

            loading.postValue(false)
        }, {
            handleError(it)
        })

        compositeDisposable.add(dis)
    }

    fun getImageUrl(item: MovieDetail) = BASE_IMAGE_URL + item.backdropPath

    private fun handleError(throwable: Throwable) {
        loading.postValue(false)
        error.postValue(true)

        Log.e(this.toString(), throwable.message.toString())
    }
}