package com.example.visiontranslator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.presentation.ui.translation.TranslationViewModel
import com.example.visiontranslator.presentation.ui.home.HomeViewModel
import com.example.visiontranslator.util.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * xxxbindsViewModelでviewmodel モジュールを定義
     */

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsTranslationsViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TranslationViewModel::class)
    abstract fun bindsImageSelectViewModel(viewModel: TranslationViewModel): ViewModel
}