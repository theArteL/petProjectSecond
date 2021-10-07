package com.artelsv.petprojectsecond.ui.moviedetail

import android.annotation.SuppressLint
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieDateReleaseUseCase: GetMovieDateReleaseUseCase
) : BaseViewModel() {
    private val mMovie = MutableLiveData<MovieDetail>(null)
    val movie: LiveData<MovieDetail> = mMovie

    private val mDateRelease = MutableLiveData<DateReleaseResult>(null)
    val dateRelease: LiveData<DateReleaseResult> = mDateRelease

    val loading = MutableLiveData(true)
    val error = MutableLiveData(false)

    // TODO: 07.10.2021 цепочка вызовов rx'a в таком виде не читаема, пиши их метод под методом
    fun getMovieDetail(movieId: Int) {
        compositeDisposable.add(
            getMovieDetailsUseCase
                .invoke(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // TODO: 07.10.2021 перепиши с использование flatMap вызова отдельного метода
                    getReleaseDate(it)
                }, {
                    handleError(it)
                })
        )
    }

    private fun getReleaseDate(movieDetail: MovieDetail, iso: String = DEFAULT_ISO) {
        compositeDisposable.add(
            getMovieDateReleaseUseCase
                .invoke(movieDetail.id, iso)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mDateRelease.postValue(it)
                    mMovie.postValue(movieDetail)

                    imageUrl.postValue(Constants.BASE_IMAGE_URL + movieDetail.backdropPath)
                    voteAsString.postValue(movieDetail.voteAverage.toString())
                    voteColor.postValue(when (movieDetail.voteAverage) {
                        in 0.0..5.0 -> R.color.red
                        in 5.1..7.0 -> R.color.yellow
                        in 7.1..10.0 -> R.color.green
                        else -> R.color.red
                    })

                    handleSuccess()
                }, {
                    handleError(it)
                })
        )
    }

    val imageUrl: MutableLiveData<String> = MutableLiveData("")
    val voteAsString: MutableLiveData<String> = MutableLiveData("")
    val voteColor: MutableLiveData<Int> = MutableLiveData(0)

    @SuppressLint("SimpleDateFormat")
    fun getMovieName(resources: Resources): String {
        val inputFormat = SimpleDateFormat(DATE_FORMAT)
        val outputFormat = SimpleDateFormat(DATE_YEAR)
        var date: Date? = null

        mDateRelease.value?.let {
            if (!it.releaseDates.isNullOrEmpty()) {
                date = inputFormat.parse(it.releaseDates.first().releaseDate)
            }
        }

        return if (date != null) {
            val formattedDate = outputFormat.format(date)

            resources.getString(R.string.movie_name_pattern, mMovie.value!!.title, formattedDate)
        } else {
            mMovie.value!!.title
        }
    }

    fun getGenresAsString(resources: Resources): String =
        mMovie.value!!.genres.joinToString(separator = resources.getString(R.string.movie_detail_separator)) {
            it.name
        }

    private fun handleSuccess() {
        loading.postValue(false)
        error.postValue(false)
    }

    private fun handleError(throwable: Throwable) {
        loading.postValue(false)
        error.postValue(true)

        Log.e(this.toString(), throwable.message.toString())
    }

    companion object {
        private const val DEFAULT_ISO = "RU"
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val DATE_YEAR = "yyyy"
    }
}