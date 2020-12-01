package com.example.visiontranslator.domain.translation

import android.net.Uri

interface TranslationUseCase {
    /**
     * 画像から文字をOCRで検出し、翻訳した文字列を取得し翻訳結果をローカルに保存する
     * OCRServiceで画像からテキストを検出
     * そのテキストをTranslateServiceで翻訳
     * 翻訳が完了したらローカルDBに保存する
     * @return ローカルDBのprimary ID
     */
    suspend fun translateText(imgUri: Uri): Long
}
