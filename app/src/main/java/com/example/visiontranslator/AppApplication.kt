package com.example.visiontranslator

import android.app.Application

/**
 * Applicationクラスでroom, daggerなどの初期化を行う
 */
class AppApplication : Application() {

    companion object {
        lateinit var component: AppApplicationComponent
    }


    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppApplicationComponent.builder()
            .appApplicationModule(AppApplicationModule(this))
            .build()
    }
}