package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.visiontranslator.domain.preview.PreviewUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class PreviewViewModel
@Inject constructor(
    context: Context,
    private val previewUseCase: PreviewUseCase
) : BaseViewModel(context.applicationContext) {

    private val _previewTranslation = MutableLiveData<Translation>()
    val previewTranslation: LiveData<Translation>
        get() = _previewTranslation

    /**
     * プレビューに表示するデータを読み込む
     */
    fun loadPreviewTranslation(id: Long) {
        processCall {
            val translation = previewUseCase.findTranslationById(id)
            _previewTranslation.postValue(translation)
        }
    }

}