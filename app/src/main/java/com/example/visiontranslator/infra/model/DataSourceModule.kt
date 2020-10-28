package com.example.visiontranslator.infra.model

import com.example.visiontranslator.infra.model.AppDatabase
import com.example.visiontranslator.infra.model.translation.TranslationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dao実装クラス、data source モジュールを定義
 */
@Module
class DataSourceModule {

    ////////////////////////////////////////
    // 記載例
    // @Singleton
    // @Binds
    // abstract fun providesXXXDataSource(
    //     useCase: XXXXUseCaseImpl
    // ): XXXXUseCase
    //
    ////////////////////////////////////////

    @Provides
    @Singleton
    fun providesTranslationDataSource(
        appDatabase: AppDatabase
    ): TranslationDao {
        return appDatabase.translationDao()
    }
}