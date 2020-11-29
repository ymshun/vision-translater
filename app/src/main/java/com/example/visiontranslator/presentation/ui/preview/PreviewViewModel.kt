package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.example.visiontranslator.domain.preview.PreviewUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.ConstantKey.ViewModelTab.PREVIEW_VIEWMODEL
import javax.inject.Inject

class PreviewViewModel
@Inject constructor(
    val context: Context,
    private val previewUseCase: PreviewUseCase
) : BaseViewModel(context.applicationContext, PREVIEW_VIEWMODEL) {

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
            this._previewTranslation.postValue(translation)
        }
    }

    /**
     * 画面のフォーカスが外れた時にTranslationモデルをローカルに保存する
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun updatePreviewTranslation() {
        processCall {
            previewUseCase.updateTranslation(this._previewTranslation.value!!)
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