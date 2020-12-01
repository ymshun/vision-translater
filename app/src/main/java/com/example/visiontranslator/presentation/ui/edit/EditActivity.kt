package com.example.visiontranslator.presentation.ui.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityEditBinding
import com.example.visiontranslator.presentation.ui.dialog.ErrorDialog
import com.example.visiontranslator.presentation.ui.dialog.LoadingTransparentDialog
import com.example.visiontranslator.presentation.ui.preview.PreviewActivity
import com.example.visiontranslator.util.EventObserver
import javax.inject.Inject

class EditActivity : AppCompatActivity(),
    ErrorDialog.ErrorDialogListener {
    companion object {
        @JvmStatic
        fun createIntent(context: Context): Intent =
            Intent(context, EditActivity::class.java).apply {
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)
    }

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppApplication.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        binding.let {
            lifecycle.addObserver(viewModel)
            it.lifecycleOwner = this@EditActivity
            it.viewModel = viewModel
        }

        setupToolbar()
        setupNavigation()
        setupDialog()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.editToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    /**
     * 各ダイアログの設定
     */
    private fun setupDialog() {
        viewModel.apply {
            // ローディングダイアログ
            loading.observe(this@EditActivity) { isLoading ->
                if (isLoading) showLoadingDialog() else closeLoadingDialog()
            }

            // エラーダイアログ
            errorMsg.observe(this@EditActivity, EventObserver { errorMsg ->
                showErrorDialog("check error log 'ERROR_TRANSLATE'\n" + errorMsg)
            })
        }
    }

    private fun showLoadingDialog() = LoadingTransparentDialog.showDialog(supportFragmentManager)

    private fun closeLoadingDialog() = LoadingTransparentDialog.closeDialog(supportFragmentManager)

    private fun showErrorDialog(errorMsg: String) = ErrorDialog.showDialog(supportFragmentManager, errorMsg = errorMsg)

    override fun onClickPosBtn(from: ErrorDialog) {}
}
