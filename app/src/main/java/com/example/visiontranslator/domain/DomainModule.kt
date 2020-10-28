package com.example.visiontranslator.domain

import com.example.visiontranslator.domain.translation.TranslationUseCase
import com.example.visiontranslator.domain.translation.TranslationUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Use caseに関するモジュールを定義
 */
@Module
abstract class DomainModule {

    ////////////////////////////////////////
    // 記載例
    // @Singleton
    // @Binds
    // abstract fun bindsXXXXUseCase(
    //     useCase: XXXXUseCaseImpl
    // ): XXXXUseCase
    //
    ////////////////////////////////////////

    @Singleton
    @Binds
    abstract fun bindsTranslationUseCase(
        useCase: TranslationUseCaseImpl
    ): TranslationUseCase

}