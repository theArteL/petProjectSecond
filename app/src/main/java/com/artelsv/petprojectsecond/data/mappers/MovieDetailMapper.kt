package com.artelsv.petprojectsecond.data.mappers

import com.artelsv.petprojectsecond.data.network.model.MovieDetailResponse
import com.artelsv.petprojectsecond.domain.model.MovieDetail

object MovieDetailMapper {
    fun toMovieDetail(movie: MovieDetailResponse) : MovieDetail {
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
            belongsToCollection = movie.belongsToCollection,
            genres = movie.genres,
            homepage = movie.homepage,
            imdbId = movie.imdbId,
            productionCompanies = movie.productionCompanies,
            productionCountries = movie.productionCountries,
            spokenLanguages = movie.spokenLanguages,
            status = movie.status,
            tagline = movie.tagline
        )
    }
}