package com.example.visiontranslator.util

import androidx.lifecycle.Observer

/**
 * LiveDataのイベント通知を observeするためのラッパークラス
 * 1度のみイベント通知を行う
 *
 * @author Yamashita 2020/09
 */

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

/**
 * event取得にnull制御かけたもの
 * すでにeventが発火している場合はnull
 *
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}

