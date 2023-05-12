package com.puzzle_agency.newsapp.features.all_news.presentation

import androidx.annotation.DrawableRes
import com.puzzle_agency.newsapp.R
import com.puzzle_agency.newsapp.features.news_shared.data.model.Article

data class AllNewsScreenState(
    val searchValue: String = "",
    val loading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val sort: AllNewsSort = AllNewsSort.PUBLISHED_AT,
    val errorDialogMessage: String? = null
)

enum class AllNewsSort(val sortValue: String, @DrawableRes val iconRes: Int) {
    PUBLISHED_AT("publishedAt", R.drawable.ic_clock),
    POPULARITY("popularity", R.drawable.ic_all_news)
}