package com.example.visiontranslator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.visiontranslator.util.Event
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val applicationContext: Context
) : ViewModel() {

    // 画像選択画面遷移
    private val _openImageSelectEvent = MutableLiveData<Event<Unit>>()
    val openImageSelectEvent: LiveData<Event<Unit>>
        get() = _openImageSelectEvent

    // 設定画面遷移
    private val _openSettingEvent = MutableLiveData<Event<Unit>>()
    val openSettingEvent: LiveData<Event<Unit>>
        get() = _openSettingEvent

    // 文字入力画面遷移
    private val _openEditEvent = MutableLiveData<Event<Unit>>()
    val openEditEvent: LiveData<Event<Unit>>
        get() = _openEditEvent

    // 文字入力画面遷移
    private val _openPreviewEvent = MutableLiveData<Event<Int>>()
    val openPreviewEvent: LiveData<Event<Int>>
        get() = _openPreviewEvent

    init {

    }

    fun loadTranslations(){

    }

    /** イベント通知系 **/

    // 画像選択画面
    fun openImageSelectEvent() {
        _openImageSelectEvent.value = Event(Unit)
    }

    // 設定画面
    fun openSettingEvent() {
        _openSettingEvent.value = Event(Unit)
    }

    // 編集画面
    fun openEditEvent() {
        _openEditEvent.value = Event(Unit)
    }

    // プレビュー画面遷移、IDを渡す
    fun openPreviewEvent(id:Int) {
        _openPreviewEvent.value = Event(id)
    }
}