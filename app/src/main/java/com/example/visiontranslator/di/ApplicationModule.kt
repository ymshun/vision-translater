package com.example.visiontranslator.di

import android.app.Application
import com.example.visiontranslator.infra.model.AppDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * applicationContext, roomモジュール定義
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplicationContext() = application.applicationContext

    @Provides
    @Singleton
    fun providesAppDataBase() = AppDatabase.getInstance(application.applicationContext)

}