package com.example.visiontranslator.infra.service.translate

/**
 * GASの翻訳済みのテキストを受け取るデータクラス
 */
data class TranslationServiceModel(
    val code: Int,      // ステータスコード
    val text: String   // 翻訳済みのテキストもしくは、エラーメッセージ
)

/**
 * GASによる翻訳APIのステータスコード
 */
enum class TranslationServiceStatusCode(val code: Int?) {
    /**
     * リクエスト成功
     */
    HTTP_STATUS_OK(200),

    /**
     * 何らかの理由により、リクエスト失敗
     */
    HTTP_STATUS_ERROR(400);

    /**
     * STATUS_CODEからenum取得
     */
    companion object {
        fun fromCode(code: Int?): TranslationServiceStatusCode =
            values().firstOrNull { it.code == code }
                ?: HTTP_STATUS_ERROR
    }

}