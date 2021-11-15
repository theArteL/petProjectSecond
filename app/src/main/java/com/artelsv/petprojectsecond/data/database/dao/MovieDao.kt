package com.artelsv.petprojectsecond.data.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.artelsv.petprojectsecond.data.entity.MovieEntity
import com.artelsv.petprojectsecond.domain.model.movie.MovieType
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert
    fun addMovie(movie: MovieEntity)

    @Insert(onConflict = IGNORE)
    fun addMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Delete
    fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE movies.id == :position")
    fun getMovieAt(position: Int): MovieEntity

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE movies.movieType == :movieType ORDER BY movies.voteAverage ASC LIMIT :pageSize OFFSET :page * :pageSize")
    fun getAllMoviesSortedByVote(
        movieType: MovieType,
        page: Int,
        pageSize: Int = PAGE_SIZE
    ): Single<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE movies.movieType == :movieType LIMIT :pageSize OFFSET :page * :pageSize")
    fun getAllMoviesByPage(
        movieType: MovieType,
        page: Int,
        pageSize: Int = PAGE_SIZE
    ): Single<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE movies.movieType == :movieType")
    fun getAllMoviesByType(movieType: MovieType): Single<List<MovieEntity>>

    companion object {
        const val PAGE_SIZE = 20
    }
}