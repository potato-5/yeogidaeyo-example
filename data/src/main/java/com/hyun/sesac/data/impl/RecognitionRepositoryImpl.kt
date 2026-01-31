package com.hyun.sesac.data.impl

import android.content.Context
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.hyun.sesac.domain.model.RegisterOCRText
import com.hyun.sesac.domain.repository.RecognitionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import androidx.core.net.toUri

class RecognitionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context, // Uri로부터 이미지를 읽으려면 Context가 필요합니다.
) : RecognitionRepository {

    // 한국어 인식기 옵션 생성
    private val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    override suspend fun extractParkingText(imageUrl: String): Result<RegisterOCRText> {
        return try {
            val uri = imageUrl.toUri()

            // 1. Uri -> InputImage 변환
            val image = InputImage.fromFilePath(context, uri)

            // 2. ML Kit 실행 (process는 Task를 반환하므로 await()로 suspend 처리)
            val result = recognizer.process(image).await()

            // 3. 결과 파싱 (여기서 원하는 로직대로 데이터를 가공합니다)
            // 예: 전체 텍스트에서 '지하', '층', '구역' 같은 키워드를 찾을 수도 있습니다.
            val fullText = result.text.replace("\n", " ").trim()

            // 지금은 일단 전체 텍스트를 다 넣어줍니다.
            val parsedZone = if(fullText.length > 10) fullText.take(10) + "..." else fullText

            Result.success(
                RegisterOCRText(
                    zone = parsedZone,
                    rawText = fullText
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}