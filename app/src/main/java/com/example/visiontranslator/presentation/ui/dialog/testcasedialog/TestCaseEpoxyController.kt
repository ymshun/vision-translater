package com.example.visiontranslator.presentation.ui.dialog.testcasedialog

import com.airbnb.epoxy.TypedEpoxyController
import com.example.visiontranslator.dialogTestcase

class TestCaseEpoxyController(
    private val clickTestImgListener: TestCaseEpoxyControllerListener
) : TypedEpoxyController<List<Int>>() {

    fun interface TestCaseEpoxyControllerListener {
        fun onClickTestImg(clickedDrawableResId: Int)
    }

    override fun buildModels(testCaseImgList: List<Int>) {
        testCaseImgList.mapIndexed { index, testImgResId ->
            dialogTestcase {
                id(modelCountBuiltSoFar)
                conroller(this@TestCaseEpoxyController)
                testCaseName("testcase ${index + 1}")
                testImgDrawableId(testImgResId)
            }
        }
    }

    fun onClickTestImg(clickedDrawableResId: Int) = clickTestImgListener.onClickTestImg(clickedDrawableResId)
}
