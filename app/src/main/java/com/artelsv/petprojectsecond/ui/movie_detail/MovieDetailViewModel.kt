package com.artelsv.petprojectsecond.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.Movie
import com.artelsv.petprojectsecond.utils.Constants.BASE_IMAGE_URL
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {
    private val mMovie = MutableLiveData<MovieDetailResponse>(null)
    val movie: LiveData<MovieDetailResponse> = mMovie

    val loading = MutableLiveData(true)

    fun setMovieValue(movie: Movie) {
        val dis = moviesRepository.getMovieDetails(movie.id).subscribeOn(Schedulers.io()).subscribe({
            mMovie.postValue(it)

            loading.postValue(false)
        }, {
            loading.postValue(false)
        })
    }

    fun getImageUrl(item: MovieDetailResponse) = BASE_IMAGE_URL + item.backdropPath
}