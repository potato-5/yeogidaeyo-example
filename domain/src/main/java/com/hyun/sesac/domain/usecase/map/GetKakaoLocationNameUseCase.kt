package com.hyun.sesac.domain.usecase.map

import com.hyun.sesac.domain.repository.KakaoApiRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

// TODO Inject 해줘야 함 ( parkingrepository )
class GetKakaoLocationNameUseCase @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): ProductResult<String?> {
        return kakaoApiRepository.getBuildingName(lat, lng)
    }
}