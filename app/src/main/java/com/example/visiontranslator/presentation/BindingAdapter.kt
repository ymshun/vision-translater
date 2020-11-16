package com.example.visiontranslator.presentation

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.visiontranslator.infra.model.translation.Translation

/**
 * BindingAdapterで使用するメソッド
 * プロジェクトの大きさによっては画面ごとに分けるほうが圧倒的に良いがこの規模ならこのファイルで一元管理する
 */

@BindingAdapter("app:glideSrc")
fun ImageView.setImageSrcString(src: String?) {
    Glide.with(this.context).load(src).into(this)
}

@BindingAdapter("app:glideSrc")
fun ImageView.setImageSrcURI(src: Uri?) {
    Glide.with(this.context).load(src).into(this)
}

@BindingAdapter("app:isVisible")
fun View.isVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("app:translationText", "app:isTranslatedText")
fun EditText.setTranslationText(translation: Translation?, isTranslatedText: Boolean) {
    this.setText(if (isTranslatedText) translation?.translatedText else translation?.originalText)
}
