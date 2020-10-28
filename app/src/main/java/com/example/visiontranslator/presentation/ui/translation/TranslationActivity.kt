package com.example.visiontranslator.presentation.ui.translation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityTranslationBinding
import com.example.visiontranslator.presentation.dialog.ErrorDialog
import com.example.visiontranslator.presentation.dialog.LoadingTransparentDialog
import com.example.visiontranslator.presentation.ui.preview.PreviewActivity
import com.example.visiontranslator.util.ConstantKey.RequestCode.REQUEST_GALLERY
import com.example.visiontranslator.util.EventObserver
import com.theartofdev.edmodo.cropper.CropImage
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class TranslationActivity : AppCompatActivity() {

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

        // dagger inject
        AppApplication.component.inject(this)

        // data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_translation)
        binding.run {
            lifecycleOwner = this@TranslationActivity
            viewmodel = viewModel
        }

        setupGallery()
        setupCropImg()
        setupDialog()
        setupNavigation()
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
        // ローディングダイアログ
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) showLoadingDialog() else closeLoadingDialog()
        }

        // エラーダイアログ
        viewModel.showErrorDialogEvent.observe(this, EventObserver { errorMsg ->
            showErrorDialog(errorMsg)
        })
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

    private fun showLoadingDialog() =
        LoadingTransparentDialog.showDialog(supportFragmentManager)

    private fun closeLoadingDialog() =
        LoadingTransparentDialog.closeDialog(supportFragmentManager)

    private fun showErrorDialog(errorMsg: String) =
        ErrorDialog.newInstance(supportFragmentManager, errorMsg = errorMsg)
}