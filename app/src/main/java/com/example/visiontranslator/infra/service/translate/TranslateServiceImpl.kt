package com.example.visiontranslator.infra.service.translate

import com.example.visiontranslator.infra.service.translate.TranslationServiceStatusCode.Companion.fromCode
import com.example.visiontranslator.infra.service.translate.TranslationServiceStatusCode.HTTP_STATUS_OK
import com.example.visiontranslator.util.ConstantKey.ErrorMsg.ERROR_GAS_REQUEST_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TranslateServiceImpl @Inject constructor() : TranslateService {

    companion object {
        const val TRANSLATE_API_URL =
            "https://script.google.com/macros/s/AKfycbytDexWycWGsVvLBZMJ0SCtDECkQm4noLKSe82kzDehlLa0OQ/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(TRANSLATE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val translateService = retrofit.create(TranslateService::class.java)

    /**
     * GASでテキストを翻訳する
     */
    override suspend fun translateText(
        text: String,
        source: String,
        target: String
    ): Response<TranslationServiceModel> = withContext(Dispatchers.Default) {
        withTimeout(10000) {
            val response = translateService.translateText(text, source, target)
            if (fromCode(response.body()?.code) == HTTP_STATUS_OK) {
                return@withTimeout response
            } else {
                throw Exception(ERROR_GAS_REQUEST_ERROR + response.errorBody().toString())
            }
        }
    }
}
