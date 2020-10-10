package com.example.visiontranslator.service.repository

import com.example.visiontranslator.service.model.Translation

/**
 * MainViewModelに提供するデータプロバイダメソッド、モデル操作メソッドの定義
 */
interface MainRepository {

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