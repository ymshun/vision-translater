package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityPreviewBinding
import com.example.visiontranslator.presentation.ui.dialog.ErrorDialog
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_IMG
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_ORIGINAL_TEXT
import com.example.visiontranslator.util.ConstantKey.FragmentTag.FRAGMENT_PREVIEW_TRANSLATED_TEXT
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

    private fun getPreviewImgFragment() =
        supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_IMG) as? PreviewImgFragment

    private fun getPreviewOriginalTextFragment() =
        supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_ORIGINAL_TEXT) as? PreviewOriginalTextFragment

    private fun getPreviewTranslatedTextFragment() =
        supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_TRANSLATED_TEXT) as? PreviewTranslatedTextFragment

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
        setupPreviewContainer()
        setupTranslationData()
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
     * preview画面に表示する初期フラグメントの設定
     * 画像が存在しない場合は
     */
    private fun setupPreviewContainer() {
        viewModel.previewContainer1.observe(this) { tag ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.previewContainer1, getPreviewFragment(tag), tag)
                .commitNow()
        }
        viewModel.previewContainer2.observe(this) { tag ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.previewContainer2, getPreviewFragment(tag), tag)
                .commitNow()
        }
    }

    /**
     * preview画面に翻訳データを反映
     */
    private fun setupTranslationData() {
        if (viewModel.previewTranslation.value == null) {
            viewModel.loadPreviewTranslation(translationId)
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

    private fun getPreviewFragment(tag: String): Fragment {
        return when (tag) {
            FRAGMENT_PREVIEW_IMG -> PreviewImgFragment.newInstance()
            FRAGMENT_PREVIEW_TRANSLATED_TEXT -> PreviewTranslatedTextFragment.newInstance()
            FRAGMENT_PREVIEW_ORIGINAL_TEXT -> PreviewOriginalTextFragment.newInstance()
            else -> PreviewTranslatedTextFragment.newInstance()
        }
    }

}