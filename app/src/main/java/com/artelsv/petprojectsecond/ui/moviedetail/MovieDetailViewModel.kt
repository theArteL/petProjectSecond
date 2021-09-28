package com.artelsv.petprojectsecond.ui.moviedetail

import android.annotation.SuppressLint
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.domain.model.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.utils.Constants.BASE_IMAGE_URL
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

    val loading = MutableLiveData(true)
    val error = MutableLiveData(false)

    fun setMovieValue(movieId: Int) {
        val dis = getMovieDetailsUseCase.invoke(movieId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            getReleaseDate(it)
        }, {
            handleError(it)
        })

        compositeDisposable.add(dis)
    }

    private fun getReleaseDate(movieDetail: MovieDetail, iso: String = DEFAULT_ISO) {
        val dis = getMovieDateReleaseUseCase.invoke(movieDetail.id, iso).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            mDateRelease.postValue(it)
            mMovie.postValue(movieDetail)

            handleSuccess()
        }, {
            handleError(it)
        })

        compositeDisposable.add(dis)
    }


    fun getImageUrl(item: MovieDetail) = BASE_IMAGE_URL + item.backdropPath

    fun getVoteAsString(item: MovieDetail) = item.voteAverage.toString()

    fun getVoteColor(item: MovieDetail) = when(item.voteAverage) {
        in 0.0..5.0 -> R.color.red
        in 5.1..7.0 -> R.color.yellow
        in 7.1..10.0 -> R.color.green
        else -> R.color.red
    }

    @SuppressLint("SimpleDateFormat")
    fun getMovieName(resources: Resources): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("yyyy")
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

    fun getGenresAsString(resources: Resources): String = mMovie.value!!.genres.joinToString(separator = resources.getString(R.string.movie_detail_separator)) {
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
    }
}