package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            it.lifecycleOwner = this@PreviewActivity
            it.viewModel = viewModel
        }

        setupTranslationData()
        setupDialog()
    }

    /**
     * preview画面に翻訳データを反映
     */
    private fun setupTranslationData() {
        viewModel.loadPreviewTranslation(translationId)

        viewModel.previewTranslation.observe(this) {
            binding.previewTranslate.setText(it.translatedText)
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
        ErrorDialog.newInstance(supportFragmentManager, errorMsg = errorMsg)
}