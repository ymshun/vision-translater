package com.example.visiontranslator.presentation.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.visiontranslator.domain.home.HomeUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.ConstantKey.ViewModelTab.HOME_VIEWMODEL
import com.example.visiontranslator.util.Event
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    context: Context,
    private val homeUseCase: HomeUseCase
) : BaseViewModel(context.applicationContext, HOME_VIEWMODEL) {

    private val _translationList = MutableLiveData<List<Translation>>()
    val translationList: LiveData<List<Translation>>
        get() = _translationList

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

    // 翻訳データプレビュー画面遷移
    private val _openPreviewEvent = MutableLiveData<Event<Long>>()
    val openPreviewEvent: LiveData<Event<Long>>
        get() = _openPreviewEvent

    /**
     * リスト表示するTranslationデータをロードする
     */
    fun loadTranslations() {
        processCall {
            _translationList.value = homeUseCase.getAllTranslations()
        }
    }

    /**
     * 検索インターフェースの設定
     * TranslationModelから部分一致する検索結果を取得
     */
    fun searchTranslation(queryWord: String) {
        processCall {
            _translationList.value = homeUseCase.findTranslationByQueryWord(queryWord)
        }
    }

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
    fun openPreviewEvent(id: Long) {
        _openPreviewEvent.value = Event(id)
    }
}