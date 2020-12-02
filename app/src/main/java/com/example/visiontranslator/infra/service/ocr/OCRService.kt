package com.example.visiontranslator.infra.service.ocr

import android.net.Uri
import com.example.visiontranslator.infra.model.translation.Translation


interface OCRService {
    /**
     * Google Vision API に画像を送り、OCRで写真からテキストを抽出する
     */
    suspend fun getDetectedDescription(detectImgUri: Uri): Translation
}
