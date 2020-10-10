package com.example.visiontranslator.service.model

import androidx.room.*

@Dao
interface TranslationDao {
    /**
     * 新規Translationモデルを保存
     */
    @Insert
    suspend fun insertTranslation(translation: Translation)

    /**
     * Translationモデルをアップデート
     */
    @Update
    suspend fun updateTranslation(translation: Translation)

    /**
     * 1つのTranslationモデルを削除
     */
    @Query("DELETE FROM translations WHERE id =:id")
    suspend fun deleteTranslationByID(id: Int)

    /**
     * すべてのTranslationsモデル削除
     */
    @Query("DELETE FROM translations")
    suspend fun deleteTranslations()

    /**
     * IDからTranslationモデル取得
     */
    @Query("SELECT * FROM translations WHERE id =:id")
    suspend fun getTranslationByID(id: Int) : Translation?

    /**
     * すべてのTranslationを取得
     */
    @Query("SELECT * FROM translations")
    fun getTranslations():List<Translation>
}