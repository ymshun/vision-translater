package com.example.visiontranslator.infra.model.translation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translations")
data class Translation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "original_text") var originalText: String,
    @ColumnInfo(name = "source_lang") var sourceLang: String = "en",
    @ColumnInfo(name = "translated_text") var translatedText: String,
    @ColumnInfo(name = "target_lang") var targetLang: String = "ja",
    @ColumnInfo(name = "img_uri") val imgUri: String = ""
//    @ColumnInfo(name = "updated_at") var updatedAt: Date
) {
//    val getImgUri: Uri
//        get() = Uri.parse(imgUri)
}
