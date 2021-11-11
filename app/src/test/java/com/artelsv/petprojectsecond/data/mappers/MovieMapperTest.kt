package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.entity.MovieEntity
import com.artelsv.petprojectsecond.data.network.model.MovieResponse
import com.artelsv.petprojectsecond.domain.model.movie.Movie
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperTest {

    private val movieResponse = MovieResponse(
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
        "11.11.2011",
        0,
        0,
        "MovieTitle",
        "MovieTitle",
        false,
        5.0,
        1000
    )
    private val movie = Movie(
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
    private val movieEntity = MovieEntity(
        0,
        false,
        "",
        1000,
        listOf(1, 2),
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

    @Test
    fun toMovieEntity() {
        assertEquals(movieEntity, MovieMapper.toMovieEntity(movie, MovieType.NOW_PLAYING))
    }

    @Test
    fun testToMovieEntity() {
        assertEquals(movieEntity, MovieMapper.toMovieEntity(movieResponse, MovieType.NOW_PLAYING))
    }

    @Test
    fun toMovie() {
        assertEquals(movie, MovieMapper.toMovie(movieResponse, MovieType.NOW_PLAYING))
    }

    @Test
    fun testToMovie() {
        assertEquals(movie, MovieMapper.toMovie(movieEntity, MovieType.NOW_PLAYING))
    }
}