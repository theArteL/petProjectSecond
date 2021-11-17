package com.artelsv.petprojectsecond.di.module.binds

import com.artelsv.petprojectsecond.data.datasource.*
import com.artelsv.petprojectsecond.data.datasource.impl.auth.AuthRemoteDataSource
import com.artelsv.petprojectsecond.data.datasource.impl.movies.MovieLocalDataSource
import com.artelsv.petprojectsecond.data.datasource.impl.movies.MovieRemoteDataSource
import com.artelsv.petprojectsecond.data.datasource.impl.user.UserLocalDataSourceImpl
import com.artelsv.petprojectsecond.data.datasource.impl.user.UserRemoteDataSource
import com.artelsv.petprojectsecond.data.datasource.impl.user.movies.UserMoviesRemoteSource
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class DataSourceBinds {

    @Binds
    @Named("movieLocalDataSource")
    abstract fun bindLocalDataSource(localDataSource: MovieLocalDataSource): MovieDataSource

    @Binds
    @Named("movieRemoteDataSource")
    abstract fun bindRemoteDataSource(remoteDataSource: MovieRemoteDataSource): MovieDataSource

    @Binds
    @Named("userRemoteDataSource")
    abstract fun bindUserRemoteDataSource(userRemoteDataSource: UserRemoteDataSource): UserDataSource

    @Binds
    @Named("userLocalDataSource")
    abstract fun bindUserLocalDataSource(userLocalDataSource: UserLocalDataSourceImpl): UserLocalDataSource

    @Binds
    @Named("authRemoteDataSource")
    abstract fun bindAuthRemoteDataSource(authRemoteDataSource: AuthRemoteDataSource): AuthDataSource

    @Binds
    @Named("userMovieDataSource")
    abstract fun bindUserMoviesRemoteDataSource(userMovieDataSource: UserMoviesRemoteSource): UserMoviesSource
}