package com.example.visiontranslator.presentation.ui.translation

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visiontranslator.domain.translation.TranslationUseCase
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao
import com.example.visiontranslator.util.ConstantKey.ErrorMsg.ERROR_NO_IMAGE
import com.example.visiontranslator.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import permissions.dispatcher.NeedsPermission
import javax.inject.Inject

class TranslationViewModel
@Inject constructor(
    private val applicationContext: Context,
    private val translateUseCase: TranslationUseCase,
    private val translationDao: TranslationDao
) : ViewModel() {

    // ギャラリーから取得した写真のUri
    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri

    // 翻訳済みのテキスト
    private val _translationId = MutableLiveData<Long>()
    val translationId: LiveData<Long>
        get() = _translationId

    // APIリクエスト中のローディングフラグ
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // ギャラリー表示イベント
    private val _showGalleryEvent = MutableLiveData<Event<Unit>>(Event(Unit))
    val showGalleryEvent: LiveData<Event<Unit>>
        get() = _showGalleryEvent

    // 画像切り抜きイベント
    private val _openCropEvent = MutableLiveData<Event<Unit>>()
    val openCropEvent: LiveData<Event<Unit>>
        get() = _openCropEvent

    // FABなどのnavigationの表示、非表示
    private val _isVisibleNav = MutableLiveData<Boolean>(true)
    val isVisibleNav: LiveData<Boolean>
        get() = _isVisibleNav

    // 翻訳データプレビュー画面遷移
    private val _openPreviewEvent = MutableLiveData<Event<Long>>()
    val openPreviewEvent: LiveData<Event<Long>>
        get() = _openPreviewEvent

    // エラーダイアログ表示イベント
    private val _showErrorDialogEvent = MutableLiveData<Event<String>>()
    val showErrorDialogEvent: LiveData<Event<String>>
        get() = _showErrorDialogEvent

    // 画面をタップして、FABなどのnavigationを表示、非表示切替
    fun onClickDisplay() {
        _isVisibleNav.value = !(_isVisibleNav.value ?: false)
    }

    // URIから画像を表示
    fun setImageUri(uri: Uri?) {
        if (uri == null) return
        _imageUri.value = uri
    }

    init {
        viewModelScope.launch {
            val id = translationDao.insertTranslation(Translation(0, "", "", "", "", ""))
            Log.d("test99", id.toString())
        }
    }

    /**
     * 翻訳APIにリクエストを送り、その結果をローカルに保存
     * 保存したTranslationモデルを取得しプレビュー画面へ遷移
     */
    @NeedsPermission(Manifest.permission.INTERNET)
    fun translate() = viewModelScope.launch {
        _imageUri.value ?: return@launch showErrorDialog(ERROR_NO_IMAGE)
        try {
            withTimeout(15000) {
                _loading.value = true
                val id = translateUseCase.translateText(_imageUri.value!!)
                _translationId.postValue(id)
                _loading.value = false
                openPreviewEvent(id)
            }
        } catch (e: Exception) {
            _loading.value = false
            showErrorDialog("check error log 'ERROR_TRANSLATE'\n" + e.message.toString())
            Log.e("ERROR_TRANSLATE", e.stackTraceToString())
        }
    }

    /** イベント通知関連 **/

    fun showGalleryEvent() {
        _showGalleryEvent.value = Event(Unit)
    }

    fun openCropEvent() {
        _openCropEvent.value = Event(Unit)
    }

    // プレビュー画面遷移、IDを渡す
    fun openPreviewEvent(id: Long) {
        _openPreviewEvent.postValue(Event(id))
    }

    // エラーダイアログを表示する
    fun showErrorDialog(errorMsg: String) {
        _showErrorDialogEvent.value = Event(errorMsg)
    }
}

