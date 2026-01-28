package com.pyo.sesac.data.impl.utils

import com.pyo.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Tutor Pyo
 * Repository 구현체에서 사용할 공통 유틸리티 함수들
 * [safeProductResultCall]
 * 단순 suspend 함수 실행 중 예외가 발생하면 RoomDBError로 감싸줌
 */
 suspend fun <T> safeProductResultCall(
    action: suspend () -> T,
): ProductResult<T> {
    return try {
        ProductResult.Success(action())
    } catch (e: Exception) {
        ProductResult.RoomDBError(e)
    }
}
/**
 * [asProductResult]
 * Flow 데이터를 감지하여 Loading -> Success -> Error 상태로 변환해주는 확장 함수
 */
 fun <T> Flow<T>.asProductResult(): Flow<ProductResult<T>> = this.map<T, ProductResult<T>> {
    ProductResult.Success(it)
}.onStart {
    emit(ProductResult.Loading)
}.catch { e ->
    emit(ProductResult.RoomDBError(e))
}