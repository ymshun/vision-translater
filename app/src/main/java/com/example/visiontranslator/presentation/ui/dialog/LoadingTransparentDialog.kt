package com.example.visiontranslator.presentation.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.DialogLoadingBinding
import com.example.visiontranslator.util.ConstantKey.DialogTag

/**
 * loadingプログレスを表示するダイアログ
 * 背景は透過している
 */
class LoadingTransparentDialog : DialogFragment() {

    companion object {
        private val newInstance: LoadingTransparentDialog = LoadingTransparentDialog()

        @JvmStatic
        fun showDialog(fm: FragmentManager, tag: String = DialogTag.DIALOG_LOADING) {
            newInstance.run {
                arguments = Bundle().apply {
                }

                val fragment = fm.findFragmentByTag(tag)
                if (fragment == null) show(fm, tag)     // 重複チェック
            }
        }

        @JvmStatic
        fun closeDialog(fm: FragmentManager, tag: String = DialogTag.DIALOG_LOADING) {
            val fragment = fm.findFragmentByTag(tag) as? DialogFragment
            fragment?.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity, R.style.TransparentDialogTheme)
            val binding = DialogLoadingBinding.inflate(LayoutInflater.from(activity))
            binding.lifecycleOwner = this@LoadingTransparentDialog
            builder.setView(binding.root)
                .setCancelable(false)
                .create()
        } ?: throw IllegalStateException("Activity not found")

    }
}