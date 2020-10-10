package com.example.visiontranslator

import com.example.visiontranslator.view.ui.activity.ImageSelectActivity
import com.example.visiontranslator.view.ui.activity.MainActivity
import com.example.visiontranslator.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * daggerでinjectするビュークラスの定義
 */

@Singleton
@Component(
    modules =
    [
        AppApplicationModule::class,
        ViewModelModule::class
//            xxxModule::class
    ]
)
interface AppApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: ImageSelectActivity)

//    fun inject(fragment:xxxFragment)
}
