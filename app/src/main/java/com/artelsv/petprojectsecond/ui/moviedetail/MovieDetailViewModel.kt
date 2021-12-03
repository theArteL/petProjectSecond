package com.artelsv.petprojectsecond.ui.moviedetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.artelsv.petprojectsecond.R
import com.artelsv.petprojectsecond.domain.model.User
import com.artelsv.petprojectsecond.domain.model.movie.DateReleaseResult
import com.artelsv.petprojectsecond.domain.model.movie.MovieDetail
import com.artelsv.petprojectsecond.domain.model.movie.credits.Cast
import com.artelsv.petprojectsecond.domain.model.movie.credits.Credits
import com.artelsv.petprojectsecond.domain.model.movie.credits.Crew
import com.artelsv.petprojectsecond.domain.usecases.movies.GetMovieDateReleaseUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieCreditsUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.GetMovieDetailsUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.RateMovieUseCase
import com.artelsv.petprojectsecond.domain.usecases.movies.detail.ToggleFavoriteMovieUseCase
import com.artelsv.petprojectsecond.domain.usecases.user.GetUserUseCase
import com.artelsv.petprojectsecond.ui.Screens
import com.artelsv.petprojectsecond.ui.base.BaseViewModel
import com.artelsv.petprojectsecond.utils.Constants
import com.artelsv.petprojectsecond.utils.exts.safeLet
import com.github.terrakok.cicerone.Router
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SuppressLint("StaticFieldLeak")
class MovieDetailViewModel @Inject constructor(
    private val context: Context,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieDateReleaseUseCase: GetMovieDateReleaseUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val rateMovieUseCase: RateMovieUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val router: Router,
) : BaseViewModel() {
    private val user = MutableLiveData<User>(null)

    private val mMovie = MutableLiveData<MovieDetail>(null)
    val movie: LiveData<MovieDetail> = mMovie

    private val mDateRelease = MutableLiveData<DateReleaseResult>(null)
    val dateRelease: LiveData<DateReleaseResult> = mDateRelease

    val credits = MutableLiveData<Credits>(null)

    val loading = MutableLiveData(true)
    val lightLoading = MutableLiveData(false)
    val error = MutableLiveData(false)
    val toastText = MutableLiveData<String>(null)

    val imageUrl: MutableLiveData<String> = MutableLiveData("")
    val voteAsString: MutableLiveData<String> = MutableLiveData("")
    val voteColor: MutableLiveData<Int> = MutableLiveData(R.color.red)
    val movieName: MutableLiveData<String> = MutableLiveData("")
    val genresAsString: MutableLiveData<String> = MutableLiveData("")

    // логика для лайков / оценок
    val favorite = MutableLiveData(false)
    val rateIt = MutableLiveData(false)
    val rating = MutableLiveData(0f)

    // адаптеры
    val castAdapter = MovieCastAdapter {
        navigateToPersonCast(it)
    }

    val crewAdapter = MovieCrewAdapter {
        navigateToPersonCrew(it)
    }

    fun getMovieDetail(movieId: Int) {
        getMovieDetailsUseCase(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                getMovieCredits(it.id)
                getReleaseDate(it)
            }
            .subscribe({
                rating.postValue(it.rating)
                favorite.postValue(it.favorite)
                handleSuccess()
            }, {
                handleError(it)
            })
            .addToComposite()

        getUserUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                user.postValue(it)
            }, {

            })
            .addToComposite()
    }

    fun toggleFavorite() {
        safeLet(movie.value, user.value) { movieDetail, user ->
            toggleFavoriteMovieUseCase(user.id, movieDetail.id, !(favorite.value ?: false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result) favorite.postValue(!(favorite.value ?: false))
                }, {

                })
                .addToComposite()
        }
    }

    fun toggleRateIt() {
        rateIt.postValue(!(rateIt.value ?: false))
    }

    fun rateMovie(value: Float) {
        movie.value?.let {
            rateMovieUseCase(it.id, value * 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result) {
                        rating.postValue(value * 2)
                    } else {
                        rating.postValue(0f)
                    }

                    toggleRateIt()

                }, { error ->
                    toastText.postValue(error.localizedMessage)
                })
                .addToComposite()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getMovieName(resources: Resources, movieDetail: MovieDetail, release: DateReleaseResult) {
        val inputFormat = SimpleDateFormat(DATE_FORMAT)
        val outputFormat = SimpleDateFormat(DATE_YEAR)
        var date: Date? = null

        if (!release.releaseDates.isNullOrEmpty()) {
            date = inputFormat.parse(release.releaseDates.first().releaseDate)
        }

        movieName.postValue(
            if (date != null) {
                val formattedDate = outputFormat.format(date)

                resources.getString(R.string.movie_name_pattern, movieDetail.title, formattedDate)
            } else {
                movieDetail.title
            }
        )
    }

    fun getGenresAsString(resources: Resources, movieDetail: MovieDetail): String =
        movieDetail.genres.joinToString(separator = resources.getString(R.string.movie_detail_separator)) {
            it.name
        }

    fun navigateBack() {
        router.exit()
    }

    fun navigateToPersonCast(cast: Cast) {
        router.navigateTo(Screens.personDetail(cast))
    }

    fun navigateToPersonCrew(crew: Crew) {
        router.navigateTo(Screens.personDetail(crew))
    }

    private fun getMovieCredits(movieId: Int) {
        getMovieCreditsUseCase(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                castAdapter.submitList(it.cast.filter { cast -> cast.knownForDepartment == context.getString(R.string.system_person_work) })
                crewAdapter.submitList(it.crew.filter { crew -> crew.knownForDepartment != context.getString(R.string.system_person_work) })
            }, {

            })
            .addToComposite()
    }

    private fun getReleaseDate(
        movieDetail: MovieDetail,
        iso: String = DEFAULT_ISO,
    ): Single<MovieDetail> {
        getMovieDateReleaseUseCase(movieDetail.id, iso)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                initData(movieDetail, it)
            }, {
                handleError(it)
            })
            .addToComposite()

        return Single.just(movieDetail)
    }

    private fun initData(movieDetail: MovieDetail, result: DateReleaseResult) {
        mDateRelease.postValue(result)
        mMovie.postValue(movieDetail)

        imageUrl.postValue(Constants.BASE_IMAGE_URL + movieDetail.backdropPath)
        voteAsString.postValue(movieDetail.voteAverage.toString())
        voteColor.postValue(
            when (movieDetail.voteAverage) {
                in 0.0..5.0 -> R.color.red
                in 5.1..7.0 -> R.color.yellow
                in 7.1..10.0 -> R.color.green
                else -> R.color.red
            }
        )
        getMovieName(context.resources, movieDetail, result)
        genresAsString.postValue(getGenresAsString(context.resources, movieDetail))
    }

    private fun handleSuccess() {
        loading.postValue(false)
        error.postValue(false)
    }

    private fun handleError(throwable: Throwable) {
        loading.postValue(false)
        error.postValue(true)

        Timber.tag(this.toString()).e(throwable.message.toString())
    }

    companion object {
        private const val DEFAULT_ISO = "RU"
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val DATE_YEAR = "yyyy"
    }
}