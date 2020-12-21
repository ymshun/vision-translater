package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.example.visiontranslator.util.ConstantKey.IntentExtraTag.INTENT_SELECTED_ID
import com.example.visiontranslator.util.EventObserver
import javax.inject.Inject

/**
 * Previewを表示するactivity
 * 複数のフラグメントがアタッチされる
 * フラグメントは同じviewModelでデータ等共有
 */
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

    // intentでpreviewに表示するモデルのIDを取得
    private var translationId: Long = 1L
    private lateinit var binding: ActivityPreviewBinding

    private val previewFragment
        get() = supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_IMG) as? PreviewImgFragment
    private val previewOriginalTextFragment
        get() = supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_ORIGINAL_TEXT) as? PreviewOriginalTextFragment
    private val getPreviewTranslatedTextFragment
        get() = supportFragmentManager.findFragmentByTag(FRAGMENT_PREVIEW_TRANSLATED_TEXT) as? PreviewTranslatedTextFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppApplication.component.inject(this)
        translationId = savedInstanceState?.getLong(INTENT_SELECTED_ID, 1L) ?: intent.getLongExtra(INTENT_SELECTED_ID, 1L)
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

    private fun setupToolbar() {
        viewModel.topBarItem.apply {
            backgroundResId.set(ContextCompat.getColor(this@PreviewActivity, R.color.colorPrimaryDark))
            leftImageResId.set(R.drawable.arrow_back)
            title.set("プレビュー")

            leftClickListener = {
                onBackPressed()
            }
        }
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
        viewModel.loadPreviewTranslation(translationId)
    }

    /**
     * ダイアログの設定
     */
    private fun setupDialog() {
        viewModel.errorMsg.observe(this, EventObserver { errorMsg ->
            showErrorDialog("check error log 'ERROR_PREVIEW'\n" + errorMsg)
        })
    }

    private fun showErrorDialog(errorMsg: String) = ErrorDialog.showDialog(supportFragmentManager, errorMsg = errorMsg)

    private fun getPreviewFragment(tag: String): Fragment {
        return when (tag) {
            FRAGMENT_PREVIEW_IMG -> PreviewImgFragment.newInstance()
            FRAGMENT_PREVIEW_TRANSLATED_TEXT -> PreviewTranslatedTextFragment.newInstance()
            FRAGMENT_PREVIEW_ORIGINAL_TEXT -> PreviewOriginalTextFragment.newInstance()
            else -> PreviewTranslatedTextFragment.newInstance()
        }
    }
}
