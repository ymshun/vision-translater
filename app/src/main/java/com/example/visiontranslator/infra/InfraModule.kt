package com.example.visiontranslator.infra

import com.example.visiontranslator.infra.repository.translations.TranslationsRepository
import com.example.visiontranslator.infra.repository.translations.TranslationsRepositoryImpl
import com.example.visiontranslator.infra.service.ocr.OCRService
import com.example.visiontranslator.infra.service.ocr.OCRServiceImpl
import com.example.visiontranslator.infra.service.translate.TranslateService
import com.example.visiontranslator.infra.service.translate.TranslateServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Repositoryを提供するためにモジュール定義
 */
@Module
abstract class InfraModule {

    /////////////////////////////
    // 記載例
    // @Singleton
    // @Binds
    // abstract fun bindsXXXRepository(
    //     repository: XXXRepositoryImpl
    // ): XXXRepository
    //
    /////////////////////////////

    @Singleton
    @Binds
    abstract fun bindsMainRepository(
        repository: TranslationsRepositoryImpl
    ): TranslationsRepository

    @Singleton
    @Binds
    abstract fun bindsOCRService(
        service: OCRServiceImpl
    ): OCRService

    @Singleton
    @Binds
    abstract fun bindsTranslateService(
        service: TranslateServiceImpl
    ): TranslateService

}