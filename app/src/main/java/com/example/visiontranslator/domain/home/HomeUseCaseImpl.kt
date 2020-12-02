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
    override suspend fun getAllTranslations(): MutableMap<Translation, Boolean> {
        return translationsRepository.getAllTranslations().asReversed().associateWith { false } as MutableMap
    }

    override suspend fun findTranslationByQueryWord(queryWord: String): MutableMap<Translation, Boolean> {
        return translationsRepository.findTranslationByQueryWord(queryWord).asReversed().associateWith { false } as MutableMap
    }

    override suspend fun deleteTranslations(translationIdList: List<Long>) = translationsRepository.deleteTranslations(translationIdList)

    override suspend fun insertTranslationTestCase() {
        for (i in 0 until 3) {
            val testcase = translationsRepository.getTranslationForTestCase()
            translationsRepository.insertTranslation(testcase)
        }
    }
}