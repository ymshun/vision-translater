package com.example.visiontranslator.presentation.ui.dialog.testcasedialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.DialogTestcaseBinding
import com.example.visiontranslator.util.ConstantKey.DialogTag.DIALOG_TESTCASE

/**
 * テストデータを入力するためのダイアログ
 * テスト画像などのデータをリストで選択できる
 */
class TestCaseDialog : DialogFragment() {
    companion object {
        private val newInstance = TestCaseDialog()

        @JvmStatic
        fun showDialog(
            fm: FragmentManager,
            tag: String = DIALOG_TESTCASE
        ) {
            newInstance.run {
                arguments = Bundle().apply {
                }

                val fragment = fm.findFragmentByTag(tag)
                if (fragment == null) show(fm, tag)
            }
        }
    }

    interface TestCaseDialogListener {
        fun onClickTestImg(from: TestCaseDialog, drawableResId: Int)
    }

    private var listener: TestCaseDialogListener? = null
    private val testImgList: List<Int> = listOf(
        R.drawable.testcase1,
        R.drawable.testcase2,
        R.drawable.testcase3,
        R.drawable.testcase4,
        R.drawable.testcase5,
        R.drawable.testcase6,
        R.drawable.testcase7,
        R.drawable.testcase8,
        R.drawable.testcase9,
        R.drawable.testcase10,
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is TestCaseDialogListener) {
            // called from activity
            context as? TestCaseDialogListener
        } else {
            // called from fragment
            parentFragment as? TestCaseDialogListener
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { activity ->
            val binding = DialogTestcaseBinding.inflate(LayoutInflater.from(activity))
            val controller = TestCaseEpoxyController() { clickedDrawableResId ->
                listener?.onClickTestImg(this@TestCaseDialog, clickedDrawableResId)
                dismiss()
            }
            controller.setData(testImgList)
            binding.dialogTestList.adapter = controller.adapter
            binding.dialogTestCloseBtn.setOnClickListener { dismiss() }

            val builder = AlertDialog.Builder(activity)
            builder.setView(binding.root)
                .create()
        } ?: throw IllegalStateException("Activity not found")
    }
}