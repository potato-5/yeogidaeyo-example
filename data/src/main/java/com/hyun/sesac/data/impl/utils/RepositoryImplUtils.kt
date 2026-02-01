package com.hyun.sesac.data.impl.utils

import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

suspend fun <T> safeProductResultCall(
    action: suspend () -> T,
): ProductResult<T> {
    return try {
        ProductResult.Success(action())
    } catch (e: Exception) {
        ProductResult.RoomDBError(e)
    }
}

fun <T> Flow<T>.asProductResult(): Flow<ProductResult<T>> = this.map<T, ProductResult<T>> {
    ProductResult.Success(it)
}.onStart {
    emit(ProductResult.Loading)
}.catch { e ->
    emit(ProductResult.RoomDBError(e))
}