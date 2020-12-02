package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.example.visiontranslator.domain.preview.PreviewUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_IMG
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_ORIGINAL_TEXT
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_TRANSLATED_TEXT
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

    // originalTextのenableフラグ
    private val _editModeOriginalText = MutableLiveData(false)
    val editModeOriginalText: LiveData<Boolean>
        get() = _editModeOriginalText

    // translatedTextのenableフラグ
    private val _editModeTranslatedText = MutableLiveData(false)
    val editModeTranslatedText: LiveData<Boolean>
        get() = _editModeTranslatedText

    // editTextのenableフラグ
    private val _isShowControlBtn = MutableLiveData(true)
    val isShowControlBtn: LiveData<Boolean>
        get() = _isShowControlBtn

    // containerに表示するフラグメントを制御するためのリスト3つのフラグメントをreplaceする
    var previewContainerList = mutableListOf(
        FRAGMENT_PREVIEW_IMG,
        FRAGMENT_PREVIEW_TRANSLATED_TEXT,
        FRAGMENT_PREVIEW_ORIGINAL_TEXT
    )

    // preview画面に表示している二つのフラグメントのタグ
    val previewContainer1 = MutableLiveData<String>(previewContainerList[0])
    val previewContainer2 = MutableLiveData<String>(previewContainerList[1])

    /**
     * プレビューに表示するデータを読み込む
     */
    fun loadPreviewTranslation(id: Long) {
        processCall {
            val translation = previewUseCase.findTranslationById(id)
            this._previewTranslation.postValue(translation)
            // Translationモデルに画像が存在しないときはfragmentリストを画像抜きの２つに
            if (translation?.imgUri == "") {
                previewContainerList = mutableListOf(
                    FRAGMENT_PREVIEW_TRANSLATED_TEXT,
                    FRAGMENT_PREVIEW_ORIGINAL_TEXT
                )
                previewContainer1.postValue(FRAGMENT_PREVIEW_ORIGINAL_TEXT)
            }
        }
    }

    /**
     * 画面のフォーカスが外れた時にTranslationモデルをローカルに保存する
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun updatePreviewTranslation() {
        processCall {
            previewUseCase.updateTranslation(_previewTranslation.value!!)
        }
    }

    // EditTextを編集可または編集不可に
    fun changeEditModeOriginalText() {
        _editModeOriginalText.value = !_editModeOriginalText.value!!
    }

    // EditTextを編集可または編集不可に
    fun changeEditModeTranslatedText() {
        _editModeTranslatedText.value = !_editModeTranslatedText.value!!
    }

    fun showControlBtn() {
        _isShowControlBtn.value = !_isShowControlBtn.value!!
    }

    // contaner1レイアウトのフラグメントを切り替える
    fun changeContainer1() {
        val tempContainerList = previewContainerList.toMutableList()
        tempContainerList.apply {
            remove(previewContainer1.value!!)
            if (previewContainerList.size == 3) remove(previewContainer2.value!!)
        }
        previewContainer1.value = tempContainerList[0]
    }

    // contaner2レイアウトのフラグメントを切り替える
    fun changeContainer2() {
        val tempContainerList = previewContainerList.toMutableList()
        tempContainerList.apply {
            if (previewContainerList.size == 3) remove(previewContainer1.value!!)
            remove(previewContainer2.value!!)
        }
        previewContainer2.value = tempContainerList[0]
    }
}
