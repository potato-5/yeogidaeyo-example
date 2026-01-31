package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.RegisterOCRText

interface RecognitionRepository {
    // 이미지 분석 후 registertest 모델로 반환하는 함수
    suspend fun extractParkingText(imageUrl: String): Result<RegisterOCRText>
}