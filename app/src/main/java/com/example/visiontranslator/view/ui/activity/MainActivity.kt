package com.example.visiontranslator.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visiontranslator.R
import com.example.visiontranslator.view.adapter.EpoxyControllerMain
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller = EpoxyControllerMain()
        controller.setData("")
        epoxyRecyclerViewMain.adapter = controller.adapter
        epoxyRecyclerViewMain.layoutManager = LinearLayoutManager(this)

    }


}