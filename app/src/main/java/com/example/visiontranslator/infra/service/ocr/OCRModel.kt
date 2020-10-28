package com.example.visiontranslator.infra.service.ocr

/**
 * OCRを行う Vision API の json data 定義
 */
data class OCRModel (
    val locale:String?,         // 検出された文字の国コード
    val description:String      // 検出された文字列
)

data class OCRListModel(
    val ocrList:List<OCRModel>
)