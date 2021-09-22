package com.artelsv.petprojectsecond.domain.interceptor

import com.artelsv.petprojectsecond.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class DefaultInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // setup timeouts
        chain.withConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .withReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .withWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)

        val original = chain.request()

        val requestBuilder = original.url.newBuilder()
            .addEncodedQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
            .addEncodedQueryParameter(QUERY_LANGUAGE, DEFAULT_LANG)
            .build()


        val request = original.newBuilder().url(requestBuilder).build()

        return chain.proceed(request)
    }

    companion object {
        private const val READ_TIME_OUT = 60
        private const val CONNECT_TIME_OUT = 60
        private const val WRITE_TIME_OUT = 60

        private const val DEFAULT_LANG = "ru-RU"

        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
    }
}