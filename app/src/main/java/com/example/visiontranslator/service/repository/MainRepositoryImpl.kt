package com.example.visiontranslator.service.repository

import android.content.Context
import androidx.room.Room
import com.example.visiontranslator.service.model.AppDatabase
import com.example.visiontranslator.service.model.Translation

/**
 * MainViewModelのデータプロバイダメソッド、モデル操作メソッドを実装したリポジトリクラス
 */
class MainRepositoryImpl(
    context: Context
) : MainRepository {
    val database = Room.databaseBuilder(context, AppDatabase::class.java, "translations").build()
    val dao = database.translationDao()

    override fun getAllTranslations(): List<Translation> = dao.getTranslations()


    override fun deleteTranslation(translationId: Int) {
    }

    override fun deleteTranslations(translationIdList: List<Int>) {
    }

}