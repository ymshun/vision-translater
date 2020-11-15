package com.example.visiontranslator.domain.preview

import com.example.visiontranslator.infra.model.translation.Translation

/**
 * preview画面で使用するapi, model操作メソッドの定義
 */
interface PreviewUseCase {
    /**
     * TranslationモデルをID から取得
     * @return Translationモデル 存在しなければnull
     */
    suspend fun findTranslationById(id: Long): Translation?
}