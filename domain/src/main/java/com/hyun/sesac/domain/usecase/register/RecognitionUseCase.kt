package com.hyun.sesac.domain.usecase.register

import com.hyun.sesac.domain.model.RegisterOCRText
import com.hyun.sesac.domain.repository.RecognitionRepository
import com.hyun.sesac.domain.result.ProductResult
import javax.inject.Inject

// TODO Inject 해줘야 함 ( parkingrepository )
class RecognitionUseCase @Inject constructor(
    private val recognitionRepository: RecognitionRepository
) {
    suspend operator fun invoke(item: String): ProductResult<RegisterOCRText>{
        return recognitionRepository.extractParkingText(item)
    }
}