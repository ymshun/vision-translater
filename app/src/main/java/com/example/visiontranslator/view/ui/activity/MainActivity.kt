package com.example.visiontranslator.view.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.visiontranslator.AppApplication
import com.example.visiontranslator.R
import com.example.visiontranslator.databinding.ActivityMainBinding
import com.example.visiontranslator.util.EventObserver
import com.example.visiontranslator.view.adapter.epoxy.EpoxyControllerMain
import com.example.visiontranslator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private var controller: EpoxyControllerMain? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppApplication.component.inject(this)
        // Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        setupEpoxy()
        setupNavigation()

    }

    /**
     * 翻訳した文と画像をリスト表示するEpoxyを生成
     *
     */
    fun setupEpoxy() {
        controller = EpoxyControllerMain(viewModel)
        controller!!.setData("")
        epoxyRecyclerViewMain.adapter = controller!!.adapter
    }


    fun setupNavigation() {
        viewModel.openImageSelectEvent.observe(this, EventObserver {
            val intent = Intent(this, ImageSelectActivity::class.java)
            startActivity(intent)
        })

        viewModel.openEditEvent.observe(this, EventObserver {
            val intent = Intent(this, ImageSelectActivity::class.java)
            startActivity(intent)
        })

        viewModel.openSettingEvent.observe(this, EventObserver {
            val intent = Intent(this, ImageSelectActivity::class.java)
            startActivity(intent)
        })

        viewModel.openPreviewEvent.observe(this, EventObserver {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        })
    }

}