package com.example.visiontranslator.infra.service.translate

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofitでリクエストを送信するAPIのメソッド
 * テキスト -> テキスト　に翻訳するAPI
 * Google App Script
 */
interface TranslateService {
    /**
     * String(オリジナル)　-> String(翻訳済み)
     * https://script.google.com/macros/s/AKfycbytDexWycWGsVvLBZMJ0SCtDECkQm4noLKSe82kzDehlLa0OQ/exec?text=hello&source=en&target=ja
     */
    @GET("exec")
    suspend fun translateText(
        @Query("text") text: String,
        @Query("source") source: String,
        @Query("target") target: String
    ): Response<TranslationServiceModel>
}
