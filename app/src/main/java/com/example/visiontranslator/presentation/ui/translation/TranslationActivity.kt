package com.example.visiontranslator.presentation.ui.translation

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityTranslationBinding
import com.example.visiontranslator.presentation.ui.dialog.ErrorDialog
import com.example.visiontranslator.presentation.ui.dialog.LoadingTransparentDialog
import com.example.visiontranslator.presentation.ui.dialog.testcasedialog.TestCaseDialog
import com.example.visiontranslator.presentation.ui.preview.PreviewActivity
import com.example.visiontranslator.util.ConstantKey.RequestCode.REQUEST_GALLERY
import com.example.visiontranslator.util.EventObserver
import com.theartofdev.edmodo.cropper.CropImage
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class TranslationActivity : AppCompatActivity(),
    TestCaseDialog.TestCaseDialogListener,
    ErrorDialog.ErrorDialogListener {

    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent =
            Intent(context, TranslationActivity::class.java).apply {
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TranslationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TranslationViewModel::class.java)
    }

    private lateinit var binding: ActivityTranslationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppApplication.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_translation)
        binding.let {
            lifecycle.addObserver(viewModel)
            it.lifecycleOwner = this@TranslationActivity
            it.viewModel = viewModel
        }

        setupToolbar()
        setupGallery()
        setupCropImg()
        setupDialog()
        setupNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GALLERY -> {
                viewModel.setImageUri(data?.data)
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result: CropImage.ActivityResult? = CropImage.getActivityResult(data)
                viewModel.setImageUri(result?.uri)
            }
            CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.translationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupGallery() {
        viewModel.showGalleryEvent.observe(this, EventObserver {
            showGallery()
        })
    }

    private fun setupCropImg() {
        viewModel.openCropEvent.observe(this, EventObserver {
            CropImage.activity(viewModel.imageUri.value).start(this)
        })
    }

    /**
     * 各ダイアログの設定
     */
    private fun setupDialog() {
        viewModel.apply {
            showTestCaseEvent.observe(this@TranslationActivity, EventObserver {
                showTestCaseDialog()
            })

            // ローディングダイアログ
            loading.observe(this@TranslationActivity) { isLoading ->
                if (isLoading) showLoadingDialog() else closeLoadingDialog()
            }

            // エラーダイアログ
            errorMsg.observe(this@TranslationActivity, EventObserver { errorMsg ->
                showErrorDialog("check error log 'ERROR_TRANSLATE'\n" + errorMsg)
            })
        }
    }

    /**
     * 画面遷移の定義
     */
    private fun setupNavigation() {
        viewModel.openPreviewEvent.observe(this, EventObserver { id ->
            val intent = PreviewActivity.createIntent(this, id)
            startActivity(intent)
        })
    }

    // galleryから写真を選択するインテント発行
    private fun showGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            setType("image/*")
        }
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun showTestCaseDialog() = TestCaseDialog.showDialog(supportFragmentManager)

    private fun showLoadingDialog() = LoadingTransparentDialog.showDialog(supportFragmentManager)

    private fun closeLoadingDialog() = LoadingTransparentDialog.closeDialog(supportFragmentManager)

    private fun showErrorDialog(errorMsg: String) = ErrorDialog.showDialog(supportFragmentManager, errorMsg = errorMsg)

    override fun onClickTestImg(from: TestCaseDialog, drawableResId: Int) {
        val uri = Uri.parse(
            "${ContentResolver.SCHEME_ANDROID_RESOURCE}://" +
                    "${resources.getResourcePackageName(drawableResId)}/" +
                    "${resources.getResourceTypeName(drawableResId)}/" +
                    resources.getResourceEntryName(drawableResId)
        )
        viewModel.setImageUri(uri)
    }

    override fun onClickPosBtn(from: ErrorDialog) {
    }
}
