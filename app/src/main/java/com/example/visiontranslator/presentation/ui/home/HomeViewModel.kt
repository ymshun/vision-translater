package com.example.visiontranslator.presentation.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.example.visiontranslator.domain.home.HomeUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.ConstantKey.ViewModelTab.HOME_VIEWMODEL
import com.example.visiontranslator.util.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import kotlin.collections.set

@FlowPreview
class HomeViewModel
@Inject constructor(
    context: Context,
    private val homeUseCase: HomeUseCase
) : BaseViewModel(context.applicationContext, HOME_VIEWMODEL) {

    // 翻訳データのリスト
    private val _translationList = object : MutableLiveData<MutableMap<Translation, Boolean>>() {
        override fun onActive() {
            processCall {
                searchViewQueryWord.asFlow()
                    .debounce(200)  // 200msecまってから検索クエリ実行
                    .distinctUntilChanged()
                    .collect {
                        if (it.trim().isNullOrEmpty()) {
                            // 全件取得
                            this.value = homeUseCase.getAllTranslations()
                        } else {
                            // 検索クエリの実行
                            this.value = homeUseCase.findTranslationByQueryWord(it)
                        }
                    }
            }
        }
    }
    val translationList: LiveData<MutableMap<Translation, Boolean>>
        get() = _translationList

    // 検索ビュークリックでフォーカスをあてる
    private val _isDeleteMode = MutableLiveData<Boolean>(false)
    val isDeleteMode: LiveData<Boolean>
        get() = _isDeleteMode

    // 検索ビュークリックでフォーカスをあてる
    private val _focusSearchViewEvent = MutableLiveData<Event<Boolean>>()
    val focusSearchViewEvent: LiveData<Event<Boolean>>
        get() = _focusSearchViewEvent

    // 画像選択画面遷移
    private val _openImageSelectEvent = MutableLiveData<Event<Unit>>()
    val openImageSelectEvent: LiveData<Event<Unit>>
        get() = _openImageSelectEvent

    // 文字入力画面遷移
    private val _openEditEvent = MutableLiveData<Event<Unit>>()
    val openEditEvent: LiveData<Event<Unit>>
        get() = _openEditEvent

    // 翻訳データプレビュー画面遷移
    private val _openPreviewEvent = MutableLiveData<Event<Long>>()
    val openPreviewEvent: LiveData<Event<Long>>
        get() = _openPreviewEvent

    // 検索クエリの変更に応じて検索結果を表示
    private val searchViewQueryWord = MutableLiveData<String>("")

    /**
     * リスト表示するTranslationデータをロードする
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadTranslations() {
        processCall {
            _translationList.postValue(homeUseCase.getAllTranslations())
        }
    }

    /**
     * 検索インターフェースの設定
     * TranslationModelから部分一致する検索結果を取得
     */
    fun setSearchQuery(queryWord: String?) {
        searchViewQueryWord.postValue(queryWord)
    }

    /**
     * Translationモデルを削除する
     */
    fun deleteTranslations() {
        val deleteIdList = _translationList.value?.filter { it.value }?.map { it.key.id } ?: return cancelDeleteMode()
        _translationList.value = _translationList.value?.filter { !it.value } as MutableMap
        processCall {
            homeUseCase.deleteTranslations(deleteIdList)
        }
        cancelDeleteMode()
    }

    // 画像長押しで削除モードに移行
    fun startDeleteMode(translation: Translation) {
        _isDeleteMode.value = true
        _translationList.value!![translation] = true
        _translationList.value = _translationList.value
    }

    // 削除モードを終了してビューを更新
    fun cancelDeleteMode() {
        _isDeleteMode.value = false
        _translationList.value = _translationList.value?.map { it.key }?.associateWith { false } as MutableMap
    }

    // テストデータの入力
    fun insertTestCaseEvent() {
        processCall {
            homeUseCase.insertTranslationTestCase()
            _translationList.postValue(homeUseCase.getAllTranslations())
        }
    }

    // searchViewのフォーカスを制御。フォーカスが外れたらクエリワードの初期化
    fun focusSearchView() {
        _focusSearchViewEvent.value = Event(_focusSearchViewEvent.value?.peekContent() ?: true)
        if (!_focusSearchViewEvent.value!!.peekContent()) searchViewQueryWord.value = ""
    }

    // 画像選択画面
    fun openImageSelectEvent() {
        if (_isDeleteMode.value!!) return
        _openImageSelectEvent.value = Event(Unit)
    }

    // 編集画面
    fun openEditEvent() {
        if (_isDeleteMode.value!!) return
        _openEditEvent.value = Event(Unit)
    }

    // プレビュー画面遷移、IDを渡す
    fun openPreviewEvent(translation: Translation) {
        if (!_isDeleteMode.value!!) {
            _openPreviewEvent.value = Event(translation.id)
        } else {
            _translationList.value = _translationList.value?.also { it[translation] = !it[translation]!! }
        }
    }
}
