package com.example.visiontranslator.presentation.ui.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.visiontranslator.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * viewModelはこれを継承する
 * コルーチン、トラッカーなどをラッピングして使いやすくしている
 * suspend関数を作ってコルーチンを使うときはsuspend関数をメインセーフティにすること
 */
abstract class BaseViewModel(
    private val applicationContext: Context,
    private val viewModelName: String
) : ViewModel(), LifecycleObserver {
    // APIリクエストなどのコルーチンのローディングフラグ
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // エラーメッセージ (エラーダイアログなどを表示)
    private val _errorMsg = MutableLiveData<Event<String>>()
    val errorMsg: LiveData<Event<String>>
        get() = _errorMsg

    /**
     * viewModelスコープでコルーチン起動
     * 通常のコルーチンなどはこれを使用
     */
    fun <T> processCall(suspendCall: suspend () -> T): Job {
        return viewModelScope.launch {
            try {
                if (_loading.value == true) return@launch
                _loading.value = true
                suspendCall.invoke()
            } catch (e: Exception) {
                _errorMsg.value = Event(e.message.toString())
                Log.e("process call error at $viewModelName", e.stackTraceToString())
            } finally {
                _loading.value = false
            }
        }
    }

}