package com.example.visiontranslator.presentation.ui.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityPreviewBinding
import com.example.visiontranslator.util.ConstantKey

class PreviewActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun createIntent(context: Context, id: Long): Intent =
            Intent(context, PreviewActivity::class.java).apply {
                putExtra(ConstantKey.IntentExtraTag.INTENT_SELECTED_ID, id)
            }
    }

    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview)
        binding.run {
            lifecycleOwner = this@PreviewActivity
//            viewModel =
        }

    }

}