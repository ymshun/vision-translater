package com.example.visiontranslator.presentation.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.home

class HomeEpoxyController(private val viewModel: HomeViewModel) :
    TypedEpoxyController<String>() {
    override fun buildModels(data: String?) {
        for (i in 0..30) {
            home {
                id(modelCountBuiltSoFar)
                viewModel(viewModel)
                title("$i: Epoxy RecyclerView Epoxy RecyclerView Epoxy RecyclerView")
//                src()
            }

        }
    }

}