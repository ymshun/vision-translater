package com.example.visiontranslator.presentation.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.home
import com.example.visiontranslator.infra.model.translation.Translation

class HomeEpoxyController(private val viewModel: HomeViewModel) :
    TypedEpoxyController<List<Translation>>() {
    override fun buildModels(translationList: List<Translation>?) {
        translationList?.map { translation ->
            home {
                id(modelCountBuiltSoFar)
                viewModel(viewModel)
                translation(translation)
//                src()
            }
        }
    }

}