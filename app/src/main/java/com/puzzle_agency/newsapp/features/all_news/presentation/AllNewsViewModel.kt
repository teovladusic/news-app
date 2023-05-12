package com.puzzle_agency.newsapp.features.all_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle_agency.newsapp.features.news_shared.data.util.FilterKeys
import com.puzzle_agency.newsapp.features.news_shared.domain.repository.NewsRepository
import com.puzzle_agency.newsapp.features.news_shared.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllNewsScreenState())
    val viewState = _viewState.asStateFlow()

    private var sortJob: Job? = null
    private var searchJob: Job? = null

    fun changeSort() {
        sortJob?.cancel()

        val currentSort = _viewState.value.sort

        val newSort = if (currentSort == AllNewsSort.POPULARITY) AllNewsSort.PUBLISHED_AT
        else AllNewsSort.POPULARITY

        _viewState.update { it.copy(sort = newSort) }

        sortJob = viewModelScope.launch {
            delay(500)
            resetResultsAndFetchNews()
        }
    }

    fun onSearchValueChange(searchString: String) {
        searchJob?.cancel()
        _viewState.update { it.copy(searchValue = searchString) }

        searchJob = viewModelScope.launch {
            delay(500)
            resetResultsAndFetchNews()
        }
    }

    private fun resetResultsAndFetchNews() {
        _viewState.update { it.copy(articles = emptyList()) }
        fetchNews()
    }

    private fun fetchNews() = viewModelScope.launch {
        val sort = _viewState.value.sort
        val searchQuery = _viewState.value.searchValue

        val filters = mapOf(
            FilterKeys.SORT to sort.sortValue,
            FilterKeys.SEARCH_QUERY to searchQuery
        )

        if (searchQuery.isBlank()) return@launch

        newsRepository.getAllNews(filters).collectLatest {
            when (it) {
                is Resource.Error -> setErrorDialog(it.message ?: "Unknown error appeared")

                is Resource.Loading -> {
                    _viewState.update { it.copy(loading = true) }
                }

                is Resource.Success -> {
                    val articles = it.data?.articles.orEmpty()
                    _viewState.update { it.copy(articles = articles, loading = false) }
                }
            }
        }
    }

    private fun setErrorDialog(error: String) {
        _viewState.update { it.copy(errorDialogMessage = error, loading = false) }
    }

    fun dismissDialog() {
        _viewState.update { it.copy(errorDialogMessage = null) }
    }
}