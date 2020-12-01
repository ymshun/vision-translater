package com.example.visiontranslator.infra.repository.translation

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.visiontranslator.R
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * TranslationModel(翻訳済みのテキストや写真などを保存するDBモデル)を操作するメソッドの定義
 * モデル単位で作成するリポジトリ
 * メインセーフティにsuspendメソッドを定義する
 */
class TranslationRepositoryImpl
@Inject constructor(
    private val context: Context,
    private val translationDao: TranslationDao
) : TranslationRepository {

    private val testImgList: List<Int> = listOf(
        R.drawable.testcase1,
        R.drawable.testcase2,
        R.drawable.testcase3,
        R.drawable.testcase4,
        R.drawable.testcase5,
        R.drawable.testcase6,
        R.drawable.testcase7,
        R.drawable.testcase8,
        R.drawable.testcase9,
        R.drawable.testcase10,
    )

    override suspend fun getAllTranslations() = withContext(Dispatchers.IO) {
        translationDao.getAllTranslations()
    }

    override suspend fun insertTranslation(translation: Translation) = withContext(Dispatchers.IO) {
        translationDao.insertTranslation(translation)
    }

    override suspend fun updateTranslation(translation: Translation) = withContext(Dispatchers.IO) {
        translationDao.updateTranslation(translation)
    }

    override suspend fun deleteTranslation(translationId: Long) = withContext(Dispatchers.IO) {
        translationDao.deleteTranslationByID(translationId)
    }

    override suspend fun deleteTranslations(translationIdList: List<Long>) = withContext(Dispatchers.IO) {
        translationDao.deleteTranslations(translationIdList)
    }

    override suspend fun deleteAllTranslations() = withContext(Dispatchers.IO) {
        translationDao.deleteAllTranslations()
    }

    override suspend fun findTranslationByID(id: Long) = withContext(Dispatchers.IO) {
        translationDao.findTranslationByID(id)
    }

    override suspend fun findTranslationByQueryWord(queryWord: String) = withContext(Dispatchers.IO) {
        translationDao.findTranslationByQueryWord(queryWord)
    }

    override fun getTranslationForTestCase(): Translation {
        val randomInt = (0 until 10).random()
        val testCaseImgUri = Uri.parse(
            "${ContentResolver.SCHEME_ANDROID_RESOURCE}://" +
                    "${context.resources.getResourcePackageName(testImgList[randomInt])}/" +
                    "${context.resources.getResourceTypeName(testImgList[randomInt])}/" +
                    context.resources.getResourceEntryName(testImgList[randomInt])
        )
        val translation = Translation(
            originalText = "",
            sourceLang = "en",
            translatedText = "test case No. $randomInt 準備中",
            targetLang = "test case No. $randomInt 準備中",
            imgUri = testCaseImgUri.toString()
        )
        return translation
    }

    override fun getOriginalTextForTestCase(): String {
        val randomInt = (0 until 4).random()
        return context.resources.getStringArray(R.array.testcaseOriginalText)[randomInt]
    }
}
