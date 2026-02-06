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
import com.hyun.sesac.data.di.IoDispatcher
import com.hyun.sesac.data.impl.utils.safeProductResultCall
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RecognitionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RecognitionRepository {
    private val recognizer =
        TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())

    override suspend fun extractParkingText(imageUrl: String): ProductResult<RegisterOCRText> {
        return withContext(ioDispatcher){
            safeProductResultCall {
                val uri = imageUrl.toUri()
                val image = InputImage.fromFilePath(context, uri)
                val result = recognizer.process(image).await()
                val fullText = result.text.replace("\n", " ").trim()
                val parsedZone = if (fullText.length > 10) fullText.take(10) + "..." else fullText

                RegisterOCRText(
                    zone = parsedZone,
                    rawText = fullText
                )
            }
        }
    }
}