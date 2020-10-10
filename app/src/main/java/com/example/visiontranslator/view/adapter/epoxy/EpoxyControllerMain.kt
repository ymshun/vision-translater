package com.example.visiontranslator.view.adapter.epoxy

import android.net.Uri
import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.main
import com.example.visiontranslator.viewmodel.MainViewModel

class EpoxyControllerMain(private val viewModel: MainViewModel) : TypedEpoxyController<String>(){
    override fun buildModels(data: String?) {
        for(i in 0..30){
            main {
                id(modelCountBuiltSoFar)
                viewModel(viewModel)
                title("$i: Epoxy RecyclerView Epoxy RecyclerView Epoxy RecyclerView")
//                src()
            }
        }
    }

}