package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityPreviewBinding
import com.example.visiontranslator.presentation.ui.dialog.ErrorDialog
import com.example.visiontranslator.util.ConstantKey.IntentExtraTag
import com.example.visiontranslator.util.EventObserver
import javax.inject.Inject

class PreviewActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun createIntent(context: Context, id: Long): Intent =
            Intent(context, PreviewActivity::class.java).apply {
                putExtra(IntentExtraTag.INTENT_SELECTED_ID, id)
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PreviewViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(PreviewViewModel::class.java)
    }

    private val translationId: Long by lazy {
        intent.getLongExtra(IntentExtraTag.INTENT_SELECTED_ID, 1L)
    }
    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppApplication.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)
        binding.let {
            lifecycle.addObserver(viewModel)
            it.lifecycleOwner = this@PreviewActivity
            it.viewModel = viewModel
        }

        setupToolbar()
        setupTranslationData()
        setupEditText()
        setupDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.previewToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * preview画面に翻訳データを反映
     */
    private fun setupTranslationData() {
        if (viewModel.previewTranslation.value == null) {
            viewModel.loadPreviewTranslation(translationId)
        }

        viewModel.previewTranslation.observe(this) {
            binding.previewTranslateText.setText(it.translatedText)
        }
        viewModel.isTranslatedText.observe(this) {
            viewModel.previewTranslation.value?.apply {
//                binding.previewTranslate.setText(if (it) translatedText else originalText)
            }
        }
    }

    /**
     * 翻訳テキストを表示するEditTextの設定
     */
    private fun setupEditText() {
        viewModel.editMode.observe(this) {
            // キーボードの表示
            if (!it) return@observe
            if (viewModel.isTranslatedText.value!!) {
                binding.previewTranslateText.isFocusableInTouchMode = it
                binding.previewTranslateText.requestFocus()
                val inputMng = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMng.showSoftInput(binding.previewTranslateText, InputMethodManager.SHOW_FORCED)
            } else {
                binding.previewOriginalText.isFocusableInTouchMode = it
                binding.previewOriginalText.requestFocus()
                val inputMng = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMng.showSoftInput(binding.previewOriginalText, InputMethodManager.SHOW_FORCED)
            }
        }
    }

    /**
     * ダイアログの設定
     */
    private fun setupDialog() {
        viewModel.errorMsg.observe(this, EventObserver { errorMsg ->
            showErrorDialog("check error log 'ERROR_PREVIEW'\\n" + errorMsg)
        })
    }

    private fun showErrorDialog(errorMsg: String) =
        ErrorDialog.showDialog(supportFragmentManager, errorMsg = errorMsg)

}