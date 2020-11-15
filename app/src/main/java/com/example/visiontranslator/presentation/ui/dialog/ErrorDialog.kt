package com.example.visiontranslator.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.visiontranslator.util.ConstantKey.DialogTag.DIALOG_ERROR

/**
 * エラーをユーザーに通知するダイアログ(通信エラーなど)
 */
class ErrorDialog : DialogFragment() {

    private var listener: ErrorDialogListener? = null

    private val errorMsg by lazy {
        arguments?.getString(ERROR_MSG)
    }

    companion object {
        private val newInstance = ErrorDialog()

        @JvmStatic
        fun newInstance(
            fm: FragmentManager,
            tag: String = DIALOG_ERROR,
            errorMsg: String
        ) {
            newInstance.run {
                arguments = Bundle().apply {
                    putString(ERROR_MSG, errorMsg)
                }

                val fragment = fm.findFragmentByTag(tag)
                if (fragment == null) show(fm, tag)
            }
        }

        @JvmStatic
        fun closeDialog(fm: FragmentManager, tag: String = DIALOG_ERROR) {
            val fragment = fm.findFragmentByTag(tag) as? DialogFragment
            fragment?.dismiss()
        }

        private const val ERROR_MSG = "ERROR_MSG"
    }

    interface ErrorDialogListener {
        fun onClickPosBtn()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is ErrorDialogListener) {
            // called from activity
            context as? ErrorDialogListener
        } else {
            // called from fragment
            parentFragment as? ErrorDialogListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ERROR")
                .setMessage(errorMsg)
                .setPositiveButton("OK") { dialog, _ ->
                    listener?.onClickPosBtn()
                }
                .create()
        } ?: throw IllegalStateException("Activity not found")

    }
}