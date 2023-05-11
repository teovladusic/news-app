package com.puzzle_agency.newsapp.features.news_shared.data.model

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: SourceResponse?,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val url: String?
)

data class SourceResponse(
    val id: String?,
    val name: String?
)