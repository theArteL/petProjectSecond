package com.artelsv.petprojectsecond.di.module

import com.artelsv.petprojectsecond.BuildConfig
import com.artelsv.petprojectsecond.data.network.MoviesService
import com.artelsv.petprojectsecond.data.network.UserService
import com.artelsv.petprojectsecond.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val READ_TIME_OUT = 60L
const val CONNECT_TIME_OUT = 60L

const val DEFAULT_LANG = "ru-RU"

const val QUERY_API_KEY = "api_key"
const val QUERY_LANGUAGE = "language"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(
        Interceptor {
            val original = it.request()

            val requestBuilder = original.url.newBuilder()
                .addEncodedQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
                .addEncodedQueryParameter(QUERY_LANGUAGE, DEFAULT_LANG)
                .build()


            val request = original.newBuilder().url(requestBuilder).build()

            return@Interceptor it.proceed(request)
        }
    ).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).build()


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit) = retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)
}