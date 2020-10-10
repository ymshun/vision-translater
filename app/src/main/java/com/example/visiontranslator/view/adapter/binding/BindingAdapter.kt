package com.example.visiontranslator.view.adapter.binding

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * BindingAdapterで使用するメソッド
 */

@BindingAdapter("app:glideSrc")
fun setImageSrcString(imageView: ImageView, src: String?) {
    Glide.with(imageView.context).load(src).into(imageView)
}

@BindingAdapter("app:glideSrc")
fun setImageSrcURI(imageView: ImageView, src: Uri?) {
    Glide.with(imageView.context).load(src).into(imageView)
}

@BindingAdapter("app:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}