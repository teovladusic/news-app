package com.puzzle_agency.newsapp.features.news_shared.data.repository

import com.puzzle_agency.newsapp.features.news_shared.data.api.NewsApi
import com.puzzle_agency.newsapp.features.news_shared.data.model.ArticleResponse
import com.puzzle_agency.newsapp.features.news_shared.data.util.safeApiCall
import com.puzzle_agency.newsapp.features.news_shared.domain.repository.NewsRepository
import com.puzzle_agency.newsapp.features.news_shared.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
): NewsRepository {

    override fun getTopNews(filters: Map<String, String>): Flow<Resource<ArticleResponse>> = safeApiCall {
        newsApi.getTopHeadlines(filters)
    }

    override fun getAllNews(filters: Map<String, String>): Flow<Resource<ArticleResponse>> = safeApiCall {
        newsApi.getAllNews(filters)
    }
}