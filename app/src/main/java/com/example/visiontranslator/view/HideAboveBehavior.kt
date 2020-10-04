package com.example.visiontranslator.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.airbnb.epoxy.EpoxyRecyclerView

class HideAboveBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<View>() {

    private var defaultChildBottomPos: Int? = null

    private var isPending = false
    private var viewState: ViewState = ViewState.STATUS_SHOW

    private enum class ViewState {
        STATUS_SHOW,     // viewが表示されている状態
        STATUS_HIDE    // viewが隠れている状態
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        // EpoxyRecyclerViewの時に変更通知を受け取る
        return target is EpoxyRecyclerView
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (defaultChildBottomPos == null) {
            // 初期のbottomポジション取得
            defaultChildBottomPos = child.bottom
        }

        if (dyConsumed < 0) {
            // 上にスクロール
            slideUp(child)
        } else {
            // 下にスクロール
            slideDown(child)
        }

//        child.translationY = (defaultChildBottomPos!! - dyConsumed).toFloat()
//        target.translationY = 56f
        Log.d("test01", dyConsumed.toString())
    }


    private fun slideDown(target: View) {
        if (!isPending && viewState == ViewState.STATUS_HIDE) {
//            target.translationY = -target.bottom.toFloat()
            target.translationY = -defaultChildBottomPos!!.toFloat()
            viewState = ViewState.STATUS_SHOW
        }
    }

    private fun slideUp(target: View) {
        if (!isPending && viewState == ViewState.STATUS_SHOW) {
            defaultChildBottomPos?.apply {
//                target.translationY = target.bottom.toFloat()
                target.translationY = defaultChildBottomPos!!.toFloat()
            }
            viewState = ViewState.STATUS_HIDE
        }
    }

//    var defaultDependencyTop = -1
//    override fun layoutDependsOn(
//        parent: CoordinatorLayout,
//        child: View,
//        dependency: View
//    ): Boolean {
//
////        return dependency is EpoxyRecyclerView
////        return dependency is FloatingActionButton
//        return dependency is LinearLayout
////        return true
//    }
//
//
//    override fun onDependentViewChanged(
//        parent: CoordinatorLayout,
//        child: View,
//        dependency: View
//    ): Boolean {
//        if (defaultDependencyTop == -1) {
//            defaultDependencyTop = dependency.top
//        }
////        child.translationY = 200f
//        child.translationY = (-dependency.top + defaultDependencyTop).toFloat()
////        dependency.translationY = (-dependency.top + defaultDependencyTop).toFloat()
////        ViewCompat.setTranslationY(child, (-dependency.top + defaultDependencyTop).toFloat())
//        Log.d("test00", dependency.top.toString())
////        Log.d("test00", child.toString())
////        Log.d("test00", child.top.toString())
//
//        return false
//    }


}