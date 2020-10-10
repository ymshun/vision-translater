package com.example.visiontranslator.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.visiontranslator.util.Event
import javax.inject.Inject

class ImageSelectViewModel
@Inject constructor(
//    private val applicationContext: Context
) : ViewModel() {

    // ギャラリーから取得した写真のUri
    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri

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

    // 画面をタップして、FABなどのnavigationを表示、非表示切替
    fun onClickDisplay() {
        _isVisibleNav.value = !(_isVisibleNav.value ?: false)
    }

    // URIから画像を表示
    fun setImageUri(uri: Uri?) {
        if (uri == null) return
        _imageUri.value = uri
    }

    /** イベント通知関連 **/

    fun showGalleryEvent() {
        _showGalleryEvent.value = Event(Unit)
    }

    fun openCropEvent() {
        _openCropEvent.value = Event(Unit)
    }
}