package com.example.visiontranslator.infra.repository.translations

import com.example.visiontranslator.infra.model.translation.Translation

/**
 * MainViewModelに提供するデータプロバイダメソッド、モデル操作メソッドの定義
 */
interface TranslationsRepository {

    /**
     * すべてのローカルTranslationモデルデータを取得
     */
    fun getAllTranslations(): List<Translation>

    /**
     * Translationモデルの削除
     */
    fun deleteTranslation(translationId: Int)

    /**
     * 複数のTranslationモデルの削除
     */
    fun deleteTranslations(translationIdList:List<Int>)

}