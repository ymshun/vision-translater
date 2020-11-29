package com.example.visiontranslator.util

/**
 * staticなキーをここに定義
 */
object ConstantKey {
    /**
     * ダイアログのタグ
     */
    object DialogTag {
        const val DIALOG_LOADING = "DIALOG_LOADING"
        const val DIALOG_TESTCASE = "DIALOG_TESTCASE"
        const val DIALOG_ERROR = "DIALOG_ERROR"
    }

    /**
     * フラグメントのタグ
     */
    object FragmentTag {

    }

    object ViewModelTab{
        const val HOME_VIEWMODEL = "homeViewModel"
        const val PREVIEW_VIEWMODEL = "previewViewModel"
        const val TRANSLATION_VIEWMODEL = "translationViewModel"
    }

    /**
     * intentのextraタグ
     */
    object IntentExtraTag {
        const val INTENT_SELECTED_ID = "INTENT_SELECTED_ID"
    }

    /**
     * リクエストコード
     */
    object RequestCode {
        const val REQUEST_GALLERY = 1
    }

    object ErrorMsg {
        // 本当はこういうstringはリソースファイルかconfigの方だが今回はここで一括管理
        const val ERROR_GOOGLE_VISION_API =
            "Error: Response of Google Vision API.\nMaybe API Service already have finished."
        const val ERROR_GAS_REQUEST_ERROR = "ERROR: Response of GAS API."
        const val ERROR_NO_IMAGE = "ERROR: No Image Selected."
    }
}