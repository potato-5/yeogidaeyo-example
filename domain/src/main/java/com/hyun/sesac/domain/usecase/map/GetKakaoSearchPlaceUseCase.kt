package com.hyun.sesac.domain.usecase.map

import com.hyun.sesac.domain.model.KakaoSearchModel
import com.hyun.sesac.domain.repository.KakaoApiRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

class GetKakaoSearchPlaceUseCase @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(
        query: String,
        lat: Double? = null,
        lng: Double? = null
    ): ProductResult<List<KakaoSearchModel>> {
        // 비즈니스 로직 추가 해도 됨
        return kakaoApiRepository.searchPlace(query, lat, lng)
    }
}