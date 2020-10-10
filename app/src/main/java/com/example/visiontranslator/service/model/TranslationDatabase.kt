package com.example.visiontranslator.service.model

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * DAOインターフェースを実装したモデル操作クラスを提供する
 */
@Database(entities = [Translation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}