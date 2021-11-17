package com.artelsv.petprojectsecond.ui.persondetail

import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.credits.Cast
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew
import com.artelsv.petprojectsecond.domain.model.persondetail.PersonDetail
import com.artelsv.petprojectsecond.domain.usecases.PersonDetailUseCase
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonDetailViewModel @Inject constructor(
    private val personDetailUseCase: PersonDetailUseCase,
    private val router: Router,
) : BaseViewModel() {
    /**
     * Основные данные для отображения
     * liveData:
     * [personDetail] - детальная информация о персоне
     * [error] - триггер ошибки
     * [cast] - первичные данные о актере
     * [crew] - первичные данные о члене съемочной команды
     * [movies] - список фильмов, где персона принимала участие в производстве
     **/
    val personDetail = MutableLiveData<PersonDetail>(null)
    val error = MutableLiveData(false)
    val cast = MutableLiveData<Cast?>(null)
    val crew = MutableLiveData<Crew?>(null)
    val movies = MutableLiveData<List<Movie>>(null)

    /**
     * Технические переменные
     * [detailIsOpen] - переменная показывающие, открыты ли детали персоны
     **/
    val detailIsOpen = MutableLiveData<Boolean>(null)

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

    fun toggleDetailExpanded() {
        if (detailIsOpen.value == null) detailIsOpen.postValue(true) else detailIsOpen.postValue(!(detailIsOpen.value ?: false))
    }

    fun navigationBack() {
        router.exit()
    }

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

    private fun cleanValues() {
        cast.postValue(null)
        crew.postValue(null)
    }
}