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
        const val DIALOG_ERROR = "DIALOG_ERROR"
    }

    /**
     * フラグメントのタグ
     */
    object FragmentTag {

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
        // 本当はこういうstringはリソースファイルかconfigの方だが
        // contextの取得が面倒なので今回はここに
        const val ERROR_GOOGLE_VISION_API =
            "Error: Response of Google Vision API.\nMaybe API Service already have finished."
        const val ERROR_GAS_REQUEST_ERROR = "ERROR: Response of GAS API."
        const val ERROR_NO_IMAGE = "ERROR: No Image Selected."
    }
}