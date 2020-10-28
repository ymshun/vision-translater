package com.example.visiontranslator

import android.app.Application
import com.example.visiontranslator.di.ApplicationComponent
import com.example.visiontranslator.di.ApplicationModule
import com.example.visiontranslator.di.DaggerApplicationComponent

/**
 * Applicationクラスでdaggerなどの初期化を行う
 */
class AppApplication : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }


    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}