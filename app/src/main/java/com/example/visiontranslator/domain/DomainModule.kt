package com.example.visiontranslator.domain

import com.example.visiontranslator.domain.edit.EditUseCase
import com.example.visiontranslator.domain.edit.EditUseCaseImpl
import com.example.visiontranslator.domain.home.HomeUseCase
import com.example.visiontranslator.domain.home.HomeUseCaseImpl
import com.example.visiontranslator.domain.preview.PreviewUseCase
import com.example.visiontranslator.domain.preview.PreviewUseCaseImpl
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
    abstract fun bindsHomeUseCase(
        useCase: HomeUseCaseImpl
    ): HomeUseCase

    @Singleton
    @Binds
    abstract fun bindsTranslationUseCase(
        useCase: TranslationUseCaseImpl
    ): TranslationUseCase

    @Singleton
    @Binds
    abstract fun bindsPreviewUseCase(
        useCase: PreviewUseCaseImpl
    ): PreviewUseCase

    @Singleton
    @Binds
    abstract fun bindsEditUseCase(
        useCase: EditUseCaseImpl
    ): EditUseCase
}
