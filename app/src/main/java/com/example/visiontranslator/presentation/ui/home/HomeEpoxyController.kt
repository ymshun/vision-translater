package com.example.visiontranslator.presentation.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.home
import com.example.visiontranslator.infra.model.translation.Translation

/**
 * home画面に表示するepoxy controller
 * translationモデルとclickのフラグをmapで受け取る
 * データの更新はcontroller#setData()を使用する
 */
class HomeEpoxyController(private val viewModel: HomeViewModel) :
    TypedEpoxyController<Map<Translation, Boolean>>() {
    override fun buildModels(translationList: Map<Translation, Boolean>?) {
        translationList?.map { map ->
            home {
                id(modelCountBuiltSoFar)
                viewModel(viewModel)
                translation(map.key)
                isClicked(map.value)
            }
        }
    }
}
