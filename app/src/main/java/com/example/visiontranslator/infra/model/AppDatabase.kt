package com.example.visiontranslator.infra.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao

/**
 * DAOインターフェースを実装したモデル操作クラスを提供する
 * getInstanceでroomDatabaseを取得する
 */
@Database(entities = [Translation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun translationDao(): TranslationDao

    // abstract fun xxxDao(): xxxDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "translations.db"
            ).build()
    }
}