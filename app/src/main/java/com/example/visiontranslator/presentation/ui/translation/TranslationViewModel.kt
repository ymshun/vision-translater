package com.example.visiontranslator.presentation.ui.translation

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.visiontranslator.domain.translation.TranslationUseCase
import com.example.visiontranslator.presentation.ui.base.BaseViewModel
import com.example.visiontranslator.util.Event
import permissions.dispatcher.NeedsPermission
import javax.inject.Inject

class TranslationViewModel
@Inject constructor(
    context: Context,
    private val translateUseCase: TranslationUseCase,
) : BaseViewModel(context.applicationContext) {

    // ギャラリーから取得した写真のUri
    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri

    // 翻訳済みのテキスト
    private val _translationId = MutableLiveData<Long>()
    val translationId: LiveData<Long>
        get() = _translationId

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

    // 画面をタップして、FABなどのnavigationを表示、非表示切替
    fun onClickDisplay() {
        _isVisibleNav.value = !(_isVisibleNav.value ?: false)
    }

    // URIから画像を表示
    fun setImageUri(uri: Uri?) {
        if (uri == null) return
        _imageUri.value = uri
    }

    /**
     * 翻訳APIにリクエストを送り、その結果をローカルに保存
     * 保存したTranslationモデルを取得しプレビュー画面へ遷移
     */
    @NeedsPermission(Manifest.permission.INTERNET)
    fun translate() {
        _imageUri.value ?: return
        processCall {
            val id = translateUseCase.translateText(_imageUri.value!!)
//            val id = 14L
            _translationId.postValue(id)
            openPreviewEvent(id)
        }
    }


//    fun translate() = viewModelScope.launch {
//        if (_loading.value == true) return@launch
//
//        try {
//            withTimeout(15000) {
//                _loading.postValue(true)
//                val id = translateUseCase.translateText(_imageUri.value!!)
//                val id = 14L
//                _translationId.postValue(id)
//                openPreviewEvent(id)
//            }
//        } catch (e: Exception) {
//            showErrorDialog("check error log 'ERROR_TRANSLATE'\n" + e.message.toString())
//            Log.e("ERROR_TRANSLATE", e.stackTraceToString())
//        } finally {
//            _loading.postValue(false)
//        }
//    }

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

}

