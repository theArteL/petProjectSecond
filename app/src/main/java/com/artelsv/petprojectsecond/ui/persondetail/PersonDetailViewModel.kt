package com.artelsv.petprojectsecond.ui.persondetail

import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.credits.Cast
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew
import com.artelsv.petprojectsecond.domain.usecases.PersonDetailUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    private val personDetailUseCase: PersonDetailUseCase,
) : BaseViewModel() {
    val error = MutableLiveData(false)
    val cast = MutableLiveData<Cast?>(null)
    val crew = MutableLiveData<Crew?>(null)
    val movies = MutableLiveData<List<Movie>>(null)

    private fun init(personId: Int) {
        personDetailUseCase.getMovies(personId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                movies.postValue(it)
            }
            .subscribe({

            }, {

            }).addToComposite()
    }

    fun setCastValue(value: Cast) {
        cast.postValue(value)

        init(value.id)
    }

    fun setCrewValue(value: Crew) {
        crew.postValue(value)

        init(value.id)
    }

    fun processErrorWithAny() {
        error.postValue(true)
    }

    private fun cleanValues() {
        cast.postValue(null)
        crew.postValue(null)
    }
}