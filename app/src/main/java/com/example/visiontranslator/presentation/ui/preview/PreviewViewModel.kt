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

    // プレビューに表示するTranslationモデル
    private val _previewTranslation = MutableLiveData<Translation>()
    val previewTranslation: LiveData<Translation>
        get() = _previewTranslation

    // editTextのenableフラグ
    private val _editMode = MutableLiveData(false)
    val editMode: LiveData<Boolean>
        get() = _editMode

    // editTextに表示するテキストが translatedText か originalTextどうかのフラグ
    private val _isTranslatedText = MutableLiveData(true)
    val isTranslatedText: LiveData<Boolean>
        get() = _isTranslatedText

    /**
     * プレビューに表示するデータを読み込む
     */
    fun loadPreviewTranslation(id: Long) {
        processCall {
            val translation = previewUseCase.findTranslationById(id)
            _previewTranslation.postValue(translation)
        }
    }

    // EditTextを編集可または編集不可に
    fun changeEditMode() {
        _editMode.value = !_editMode.value!!
    }

    // 翻訳済みのテキストとオリジナルの翻訳前のOCR検出したテキストを切り替える
    fun changeTranslateText() {
        _isTranslatedText.value = !_isTranslatedText.value!!
    }
}