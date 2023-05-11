package com.puzzle_agency.newsapp.features.news_shared.domain.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: Int? = null
) {

    class Success<T>(data: T) : Resource<T>(data = data)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
    class Error<T>(message: String?) : Resource<T>(message = message, status = 0)
}