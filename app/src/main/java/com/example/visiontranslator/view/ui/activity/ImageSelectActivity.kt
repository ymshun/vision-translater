package com.example.visiontranslator.view.ui.activity

import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityImageSelectBinding
import com.example.visiontranslator.util.ConstantKey.RequestCode.REQUEST_GALLERY
import com.example.visiontranslator.util.EventObserver
import com.example.visiontranslator.viewmodel.ImageSelectViewModel
import com.theartofdev.edmodo.cropper.CropImage
import javax.inject.Inject

class ImageSelectActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ImageSelectViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ImageSelectViewModel::class.java)
    }

    private lateinit var binding: ActivityImageSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // dagger inject
        AppApplication.component.inject(this)

        // data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_select)
        binding.run {
            lifecycleOwner = this@ImageSelectActivity
            viewmodel = viewModel
        }

        setupGallery()
        setupCropImg()
    }


    private fun setupGallery() {
        viewModel.showGalleryEvent.observe(this, EventObserver {
            showGallery()
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

    private fun setupCropImg() {
        viewModel.openCropEvent.observe(this, EventObserver {
            CropImage.activity(viewModel.imageUri.value).start(this)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_GALLERY -> {
                viewModel.setImageUri(data?.data)
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result : CropImage.ActivityResult? = CropImage.getActivityResult(data)
                viewModel.setImageUri(result?.uri)
            }
            CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {

            }
        }
    }
}