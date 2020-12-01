package com.example.visiontranslator.domain.home

import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.repository.translation.TranslationRepository
import javax.inject.Inject

/**
 * UseCaseは今プロジェクトにおいては画面単位で作成する
 *
 * ホーム画面HomeActivityで使用するuseCaseを実装したクラス
 * リスト表示をする画面
 */
class HomeUseCaseImpl
@Inject constructor(
    private val translationsRepository: TranslationRepository
) : HomeUseCase {
    override suspend fun getAllTranslations(): List<Translation> = translationsRepository.getAllTranslations()

    override suspend fun findTranslationByQueryWord(queryWord: String): List<Translation> = translationsRepository.findTranslationByQueryWord(queryWord)

    override suspend fun deleteTranslations(translationIdList: List<Long>) = translationsRepository.deleteTranslations(translationIdList)

    override suspend fun insertTranslationTestCase() {
        val testcase = translationsRepository.getTranslationForTestCase()
        translationsRepository.insertTranslation(testcase)
    }
}