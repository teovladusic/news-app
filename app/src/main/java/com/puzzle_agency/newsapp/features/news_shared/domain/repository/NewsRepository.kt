package com.puzzle_agency.newsapp.features.news_shared.domain.repository

import com.puzzle_agency.newsapp.features.news_shared.data.model.ArticleResponse
import com.puzzle_agency.newsapp.features.news_shared.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {

    fun getTopNews(filters: Map<String, String>): Flow<Resource<ArticleResponse>>
}