package com.artelsv.petprojectsecond.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.base.BaseViewModel
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.MovieDetail
import com.artelsv.petprojectsecond.utils.Constants.BASE_IMAGE_URL
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : BaseViewModel() {
    private val mMovie = MutableLiveData<MovieDetail>(null)
    val movie: LiveData<MovieDetail> = mMovie

    val loading = MutableLiveData(true)
    val error = MutableLiveData(false)

    fun setMovieValue(movieId: Int) {
        val dis = moviesRepository.getMovieDetails(movieId).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({
            mMovie.postValue(it)

            loading.postValue(false)
        }, {
            loading.postValue(false)

            error.postValue(true)
        })

        compositeDisposable.add(dis)
    }

    fun getImageUrl(item: MovieDetail) = BASE_IMAGE_URL + item.backdropPath
}