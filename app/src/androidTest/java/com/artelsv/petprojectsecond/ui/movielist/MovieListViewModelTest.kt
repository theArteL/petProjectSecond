package com.artelsv.petprojectsecond.ui.movielist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artelsv.petprojectsecond.data.database.MoviesDatabase
import com.artelsv.petprojectsecond.data.datasource.*
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.repository.MoviesRepositoryImpl
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.interceptor.DefaultInterceptor
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetNowPlayingMoviesUseCaseImpl
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCase
import com.artelsv.petprojectsecond.domain.usecases.GetPopularMoviesUseCaseImpl
import com.artelsv.petprojectsecond.utils.Constants
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest : TestCase() {

    private lateinit var movieListViewModel: MovieListViewModel

    @Before
    override fun setUp() {
        super.setUp()

        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).allowMainThreadQueries().build()

        val localDataSource: MovieDataSource = MovieLocalDataSource(
            db.getMovieDao()
        )

        val client = OkHttpClient().newBuilder()
            .addInterceptor(DefaultInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val remoteDataSource: MovieDataSource = MovieRemoteDataSource(
            retrofit.create(MoviesService::class.java)
        )

        val moviesRepository: MoviesRepository = MoviesRepositoryImpl(
            localDataSource,
            remoteDataSource,
            object : NowPlayingMoviePagingSource.Factory {
                override fun create(): NowPlayingMoviePagingSource {
                    return NowPlayingMoviePagingSource(localDataSource, remoteDataSource)
                }
            },

            object : PopularMoviePagingSource.Factory {
                override fun create(): PopularMoviePagingSource {
                    return PopularMoviePagingSource(localDataSource, remoteDataSource)
                }
            },
        )

        val popularUseCase: GetPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(
            moviesRepository
        )

        val nowPlayingUseCase: GetNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCaseImpl(
            moviesRepository
        )

        movieListViewModel = MovieListViewModel(popularUseCase, nowPlayingUseCase)
    }

    @Test
    fun popularPagingData() {
        movieListViewModel.popularPagingData.isEmpty.map {
            assertEquals(false, it)
        }
    }
}