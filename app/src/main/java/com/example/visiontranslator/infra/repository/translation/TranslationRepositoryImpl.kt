package com.example.visiontranslator.infra.repository.translation

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.visiontranslator.R
import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
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
        val randomInt = (0 until 13).random()
        val testCaseImgUri = Uri.parse(
            "${ContentResolver.SCHEME_ANDROID_RESOURCE}://" +
                    "${context.resources.getResourcePackageName(testImgList[randomInt])}/" +
                    "${context.resources.getResourceTypeName(testImgList[randomInt])}/" +
                    context.resources.getResourceEntryName(testImgList[randomInt])
        )
        val translation = Translation(
            originalText = loadTextFile(testCaseOriginalTxtFile[randomInt]),
            sourceLang = "en",
            translatedText = loadTextFile(testCaseTranslatedTxtFile[randomInt]),
            targetLang = "ja",
            imgUri = if (randomInt > 3) testCaseImgUri.toString() else ""
        )
        return translation
    }

    override fun getOriginalTextForTestCase(): String {
        val randomInt = (0 until 16).random()
        return loadTextFile(testCaseOriginalTxtFile[randomInt])
    }

    private fun loadTextFile(fileName: String): String {
        var text = ""
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        try {
            // assetsフォルダ内の sample.txt をオープンする
            inputStream = context.assets.open(fileName)
            bufferedReader = BufferedReader(InputStreamReader(inputStream))

            // １行ずつ読み込み、改行を付加する
            var str: String
            while (bufferedReader.readLine().also { str = it } != null) {
                text += str + "\n"
            }
        } catch (e: Exception) {
        } finally {
            inputStream?.close()
            bufferedReader?.close()
        }
        return text
    }

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
        R.drawable.testcase11,
        R.drawable.testcase12,
        R.drawable.testcase13
    )

    private val testCaseOriginalTxtFile = listOf(
        "testcase1_0.txt",
        "testcase2_0.txt",
        "testcase3_0.txt",
        "testcase4_0.txt",
        "testcase5_0.txt",
        "testcase6_0.txt",
        "testcase7_0.txt",
        "testcase8_0.txt",
        "testcase9_0.txt",
        "testcase10_0.txt",
        "testcase11_0.txt",
        "testcase12_0.txt",
        "testcase13_0.txt",
        "testcase14_0.txt",
        "testcase15_0.txt",
        "testcase16_0.txt",
        "testcase17_0.txt"
    )

    private val testCaseTranslatedTxtFile = listOf(
        "testcase1_1.txt",
        "testcase2_1.txt",
        "testcase3_1.txt",
        "testcase4_1.txt",
        "testcase5_1.txt",
        "testcase6_1.txt",
        "testcase7_1.txt",
        "testcase8_1.txt",
        "testcase9_1.txt",
        "testcase10_1.txt",
        "testcase11_1.txt",
        "testcase12_1.txt",
        "testcase13_1.txt"
    )
}
