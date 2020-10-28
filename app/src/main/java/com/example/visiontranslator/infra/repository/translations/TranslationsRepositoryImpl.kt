package com.example.visiontranslator.infra.repository.translations

import android.util.Log
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao
import javax.inject.Inject

/**
 * MainViewModelのデータプロバイダメソッド、モデル操作メソッドを実装したリポジトリクラス
 */
class TranslationsRepositoryImpl
@Inject constructor(
    private val translationDao: TranslationDao
//    private val appDatabase: AppDatabase
) : TranslationsRepository {

    override fun getAllTranslations(): List<Translation> = listOf()

    override fun deleteTranslation(translationId: Int) {
        Log.d("test00", "RepositoryImpl DELETE")

    }

    override fun deleteTranslations(translationIdList: List<Int>) {
    }

}