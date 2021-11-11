package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.*
import com.artelsv.petprojectsecond.domain.model.movie.*
import org.junit.Assert.assertEquals
import org.junit.Test


class MovieDetailMapperTest {

    private val genreResponse = GenreResponse(0, "Genre")
    private val companyResponse = CompanyResponse(0, "", "Name", "Russia")
    private val countryResponse = CountryResponse("iso", "Name")
    private val languageResponse = LanguageResponse("EnglishName", "iso", "Name")

    private val movieDetailResponse = MovieDetailResponse(
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
        5.0f,
        1000,
        listOf(genreResponse, genreResponse),
        "",
        "",
        listOf(companyResponse, companyResponse),
        listOf(countryResponse, countryResponse),
        listOf(languageResponse, languageResponse),
        "Ok",
        ""
    )

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
        5.0f,
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

    @Test
    fun toMovieDetail() {
        assertEquals(movieDetail, MovieDetailMapper.toMovieDetail(movieDetailResponse))
    }

    @Test
    fun toGenre() {
        assertEquals(genre, MovieDetailMapper.toGenre(genreResponse))
    }

    @Test
    fun toCompany() {
        assertEquals(company, MovieDetailMapper.toCompany(companyResponse))
    }

    @Test
    fun toCountry() {
        assertEquals(country, MovieDetailMapper.toCountry(countryResponse))
    }

    @Test
    fun toLanguage() {
        assertEquals(language, MovieDetailMapper.toLanguage(languageResponse))
    }
}