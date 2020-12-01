package com.example.visiontranslator.domain.edit

interface EditUseCase {
    /**
     * 引数のテキストをAPIにリクエストをして翻訳し、その結果をlocal DBに保存
     * @return 保存したTranslationモデルの primary id
     */
    suspend fun translate(originalText: String) : Long

    /**
     *
     */
    fun getTestCaseForOriginalText() : String
}