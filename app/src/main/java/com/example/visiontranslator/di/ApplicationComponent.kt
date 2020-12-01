package com.example.visiontranslator.di

import com.example.visiontranslator.domain.DomainModule
import com.example.visiontranslator.infra.InfraModule
import com.example.visiontranslator.infra.model.DataSourceModule
import com.example.visiontranslator.presentation.ViewModelModule
import com.example.visiontranslator.presentation.ui.edit.EditActivity
import com.example.visiontranslator.presentation.ui.home.HomeActivity
import com.example.visiontranslator.presentation.ui.preview.PreviewActivity
import com.example.visiontranslator.presentation.ui.preview.PreviewImgFragment
import com.example.visiontranslator.presentation.ui.preview.PreviewOriginalTextFragment
import com.example.visiontranslator.presentation.ui.preview.PreviewTranslatedTextFragment
import com.example.visiontranslator.presentation.ui.translation.TranslationActivity
import dagger.Component
import javax.inject.Singleton

/**
 * daggerでinjectするビュークラスの定義
 */

@Singleton
@Component(
    modules =
    [
        ApplicationModule::class,
        ViewModelModule::class,
        InfraModule::class,
        DataSourceModule::class,
        DomainModule::class
        // xxxModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: HomeActivity)
    fun inject(activity: EditActivity)
    fun inject(activity: TranslationActivity)
    fun inject(activity: PreviewActivity)
    // fun inject(activity: xxxActivity)

    fun inject(fragment: PreviewImgFragment)
    fun inject(fragment: PreviewOriginalTextFragment)
    fun inject(fragment: PreviewTranslatedTextFragment)
    // fun inject(fragment: xxxFragment)
}
