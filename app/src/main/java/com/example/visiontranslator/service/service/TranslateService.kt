package com.example.visiontranslator.service.service

/**
 * Retrofitでリクエストを送信するAPIのメソッド
 * テキスト -> テキスト　に翻訳するAPI
 * Google App Script
 */
interface TranslateService{
    /**
     * String(オリジナル)　-> String(翻訳済み)
     */
    suspend fun translateText()
}