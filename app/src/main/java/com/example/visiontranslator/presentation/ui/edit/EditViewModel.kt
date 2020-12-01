package com.example.visiontranslator.presentation.ui.edit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.visiontranslator.domain.edit.EditUseCase
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.ConstantKey.ViewModelTab.EDIT_VIEWMODEL
import com.example.visiontranslator.util.Event
import javax.inject.Inject

class EditViewModel
@Inject constructor(
    context: Context,
    private val editUseCase: EditUseCase
) : BaseViewModel(context.applicationContext, EDIT_VIEWMODEL) {

    val originalText = MutableLiveData<String>()

    // 翻訳データプレビュー画面遷移
    private val _openPreviewEvent = MutableLiveData<Event<Long>>()
    val openPreviewEvent: LiveData<Event<Long>>
        get() = _openPreviewEvent

    /**
     * 入力されたテキストデータを取得してAPIにリクエストを送り翻訳する
     */
    fun translate() {
        originalText.value ?: return
        processCall {
            val id = editUseCase.translate(originalText.value!!)
            openPreviewEvent(id)
        }
    }

    // テスト翻訳用のoriginalテキストを取得、ビューに反映
    fun insertTestCase() {
        originalText.value = editUseCase.getTestCaseForOriginalText()
    }

    // プレビュー画面遷移、IDを渡す
    fun openPreviewEvent(id: Long) {
        _openPreviewEvent.postValue(Event(id))
    }
}
