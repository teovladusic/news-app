package com.puzzle_agency.newsapp.features.top_news.presentation

import com.puzzle_agency.newsapp.features.news_shared.data.model.Article

data class TopNewsScreenState(
    val articles: List<Article> = emptyList(),
    val topArticles: List<Article> = emptyList(),
    val errorDialogMessage: String? = null,
    val canPaginate: Boolean = true,
    val loading: Boolean = false
)