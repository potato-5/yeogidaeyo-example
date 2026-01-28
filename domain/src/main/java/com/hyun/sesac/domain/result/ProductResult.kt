package com.pyo.sesac.domain.result

sealed class ProductResult<out T> {
    data object NoConstructor : ProductResult<Nothing>()
    data object Loading : ProductResult<Nothing>()

    data class Success<T>(val resultData: T) : ProductResult<T>()

    data class NetworkError(val exception: Throwable) : ProductResult<Nothing>()
    data class RoomDBError(val exception: Throwable) : ProductResult<Nothing>()
}