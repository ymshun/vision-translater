package com.example.visiontranslator

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppApplicationModule(private val application:Application){

    @Provides
    @Singleton
    fun providesApplicationContext() = application.applicationContext
}