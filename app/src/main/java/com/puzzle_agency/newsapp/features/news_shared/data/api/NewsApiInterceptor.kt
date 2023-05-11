package com.puzzle_agency.newsapp.features.news_shared.data.api

import com.puzzle_agency.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NewsApiInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val apiKey = BuildConfig.API_KEY

        val url = original.url.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}