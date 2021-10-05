package com.artelsv.petprojectsecond.data.fakerepositories

import androidx.paging.PagingData
import com.artelsv.petprojectsecond.domain.MoviesRepository
import com.artelsv.petprojectsecond.domain.model.*
import io.reactivex.Flowable
import io.reactivex.Single

class FakeMovieRepository : MoviesRepository {

    private val movieNowPlaying = Movie(
        false,
        "",
        1000,
        listOf(1, 2),
        0,
        "Russian",
        "MovieTitle",
        "MovieOverview",
        5.0,
        "",
        "11.11.2011",
        0,
        0,
        "MovieTitle",
        false,
        5.0,
        1000
    )

    private val moviePopular = Movie(
        false,
        "",
        1000,
        listOf(1, 2),
        0,
        "Russian",
        "MovieTitle",
        "MovieOverview",
        5.0,
        "",
        "11.11.2011",
        0,
        0,
        "MovieTitle",
        false,
        5.0,
        1000,
        movieType = MovieType.POPULAR
    )

    private val dateRelease = DateRelease("cert", "iso", "11.11.2011", 0)
    private val dateReleaseResultList = DateReleaseResult("iso", listOf(dateRelease, dateRelease))

    private val genre = Genre(0, "Genre")
    private val company = Company(0, "Name", "", "Russia")
    private val country = Country("iso", "Name")
    private val language = Language("EnglishName", "iso", "Name")

    private val movieDetail = MovieDetail(
        false,
        "",
        0,
        listOf(1, 2),
        0,
        "Russian",
        "MovieTitle",
        "MovieOverview",
        5.0,
        "",
        "11.11.2011",
        0,
        0,
        "MovieTitle",
        false,
        5.0,
        1000,
        listOf(genre, genre),
        "",
        "",
        listOf(company, company),
        listOf(country, country),
        listOf(language, language),
        "Ok",
        ""
    )

    override fun getPopularMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>> {
        return when (movieSortType) {
            MovieSortType.NO -> Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
            MovieSortType.ASC -> Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
            MovieSortType.DESC -> Flowable.just(PagingData.from(listOf(moviePopular, moviePopular)))
        }
    }

    override fun getNowPlayingMovies(movieSortType: MovieSortType): Flowable<PagingData<Movie>> {
        return when (movieSortType) {
            MovieSortType.NO -> Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
            MovieSortType.ASC -> Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
            MovieSortType.DESC -> Flowable.just(PagingData.from(listOf(movieNowPlaying, movieNowPlaying)))
        }
    }

    override fun getMovieDateRelease(movieId: Int): Single<List<DateReleaseResult>> = Single.just(
        listOf(dateReleaseResultList, dateReleaseResultList)
    )

    override fun getMovieDetails(movieId: Int): Single<MovieDetail> = Single.just(
        movieDetail
    )

}