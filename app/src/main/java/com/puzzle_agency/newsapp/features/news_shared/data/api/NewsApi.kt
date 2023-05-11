package com.puzzle_agency.newsapp.features.news_shared.data.api

import com.puzzle_agency.newsapp.features.news_shared.data.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @QueryMap filters: Map<String, String>
    ): Response<ArticleResponse>
}