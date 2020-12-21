package com.example.visiontranslator.presentation.ui.base

import androidx.databinding.ObservableField

typealias TopBarClickListener = (() -> Unit)?

/**
 * toolbarを共通化して管理
 */
class ToolBarItem {

    val backgroundResId: ObservableField<Int> = ObservableField()
    val title: ObservableField<String> = ObservableField()
    val leftImageResId: ObservableField<Int> = ObservableField()
    val rightImageResId: ObservableField<Int> = ObservableField()

    var leftClickListener: TopBarClickListener = null
    var rightClickListener: TopBarClickListener = null

    fun onClickResLeft() {
        leftClickListener?.let {
            it()
        }
    }

    fun onClickResRight() {
        rightClickListener?.let {
            it()
        }
    }
}