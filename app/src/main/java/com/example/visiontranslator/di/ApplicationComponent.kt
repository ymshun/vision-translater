package com.example.visiontranslator.di

import com.example.visiontranslator.domain.DomainModule
import com.example.visiontranslator.infra.InfraModule
import com.example.visiontranslator.infra.model.DataSourceModule
import com.example.visiontranslator.presentation.ViewModelModule
import com.example.visiontranslator.presentation.ui.translation.TranslationActivity
import com.example.visiontranslator.presentation.ui.home.HomeActivity
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
//            xxxModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: HomeActivity)
    fun inject(activity: TranslationActivity)

//    fun inject(fragment:xxxFragment)
}
