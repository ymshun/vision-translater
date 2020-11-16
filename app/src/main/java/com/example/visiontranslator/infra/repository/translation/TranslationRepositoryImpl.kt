package com.example.visiontranslator.infra.repository.translation

import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.model.translation.TranslationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * TranslationModel(翻訳済みのテキストや写真などを保存するDBモデル)を操作するメソッドの定義
 * メインセーフティにsuspendメソッドを定義する
 */
class TranslationRepositoryImpl
@Inject constructor(
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

    override suspend fun deleteTranslations(translationIdList: List<Long>) =
        withContext(Dispatchers.IO) {
            translationDao.deleteTranslations(translationIdList)
        }

    override suspend fun deleteAllTranslations() = withContext(Dispatchers.IO) {
        translationDao.deleteAllTranslations()
    }

    override suspend fun findTranslationByID(id: Long) = withContext(Dispatchers.IO) {
        translationDao.findTranslationByID(id)
    }

    override suspend fun findTranslationByQueryWord(queryWord: String) =
        withContext(Dispatchers.IO) {
            translationDao.findTranslationByQueryWord(queryWord)
        }
}