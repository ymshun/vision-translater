package com.example.visiontranslator.service.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "translations")
data class Translation(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "original_text") var originalText: String,
    @ColumnInfo(name = "translated_text") var translatedText: String,
    @ColumnInfo(name = "img_uri") val imgUri: String,
    @ColumnInfo(name = "updated_at") var updatedAt: Date
) {
    val getImgUri: Uri
        get() = Uri.parse(imgUri)
}