package com.example.visiontranslator.infra

import com.example.visiontranslator.infra.repository.translation.TranslationRepository
import com.example.visiontranslator.infra.repository.translation.TranslationRepositoryImpl
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
        repository: TranslationRepositoryImpl
    ): TranslationRepository

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