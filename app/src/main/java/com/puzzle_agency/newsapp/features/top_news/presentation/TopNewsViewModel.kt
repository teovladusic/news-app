package com.puzzle_agency.newsapp.features.top_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.newsapp.features.news_shared.data.model.Article
import com.puzzle_agency.newsapp.features.news_shared.data.util.FilterKeys
import com.puzzle_agency.newsapp.features.news_shared.domain.repository.NewsRepository
import com.puzzle_agency.newsapp.features.news_shared.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    companion object {
        private const val COUNTRY_CODE = "us"
        const val PAGE_SIZE = 10
    }

    private val _viewState = MutableStateFlow(TopNewsScreenState())
    val viewState = _viewState.asStateFlow()

    var currentPage = 1
        private set

    init {
        fetchNews()
    }

    fun loadNewPage() {
        if (_viewState.value.loading) return
        currentPage += 1
        fetchNews()
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refresh() = viewModelScope.launch {
        currentPage = 1
        _viewState.update {
            it.copy(
                articles = emptyList(), topArticles = emptyList(),
                canPaginate = true, loading = false
            )
        }

        _isRefreshing.emit(false)

        fetchNews()
    }

    private fun fetchNews() = viewModelScope.launch {
        val filters = mapOf(
            FilterKeys.COUNTRY to COUNTRY_CODE,
            FilterKeys.PAGE to currentPage.toString(),
            FilterKeys.PAGE_SIZE to PAGE_SIZE.toString()
        )

        newsRepository.getTopNews(filters).collectLatest {
            when (it) {
                is Resource.Error -> setErrorDialog(it.message ?: "Unknown error appeared")

                is Resource.Loading -> {
                    _viewState.update { it.copy(loading = true) }
                }

                is Resource.Success -> {
                    val articles = it.data?.articles.orEmpty()
                    addArticlesToList(articles)
                }
            }
        }
    }

    private fun addArticlesToList(newArticles: List<Article>) {
        var currentArticles = _viewState.value.articles.toMutableList()
        currentArticles.addAll(newArticles)

        val currentTopArticles = _viewState.value.topArticles

        val shouldAddArticlesToTopList = currentTopArticles.isEmpty()

        if (shouldAddArticlesToTopList) {
            val topArticles = currentArticles.take(5)
            _viewState.update { it.copy(topArticles = topArticles) }

            currentArticles = currentArticles.mapIndexedNotNull { index, article ->
                if (index < 5) null
                else article
            }.toMutableList()
        }

        _viewState.update { it.copy(articles = currentArticles, loading = false) }
    }

    private fun setErrorDialog(error: String) {
        _viewState.update { it.copy(errorDialogMessage = error, loading = false) }
    }
}