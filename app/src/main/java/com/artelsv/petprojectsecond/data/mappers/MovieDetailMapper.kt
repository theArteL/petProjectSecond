package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.*
import com.artelsv.petprojectsecond.domain.model.movie.*

object MovieDetailMapper {
    fun toMovieDetail(movie: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            budget = movie.budget,
            genreIds = movie.genreIds,
            id = movie.id,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            revenue = movie.revenue,
            runtime = movie.runtime,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
//            belongsToCollection = movie.belongsToCollection,
            genres = movie.genres.map(::toGenre),
            homepage = movie.homepage,
            imdbId = movie.imdbId,
            productionCompanies = movie.productionCompanies.map(::toCompany),
            productionCountries = movie.productionCountries.map(::toCountry),
            spokenLanguages = movie.spokenLanguages.map(::toLanguage),
            status = movie.status,
            tagline = movie.tagline
        )
    }

    fun toGenre(genre: GenreResponse) = Genre(genre.id, genre.name)

    fun toCompany(company: CompanyResponse) = Company(company.id, company.name, company.logoPath, company.originCountry)

    fun toCountry(country: CountryResponse) = Country(country.iso, country.name)

    fun toLanguage(language: LanguageResponse) = Language(language.englishName, language.iso, language.name)
}