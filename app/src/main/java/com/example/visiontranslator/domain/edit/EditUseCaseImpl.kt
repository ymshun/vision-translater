package com.example.visiontranslator.domain.edit

import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.repository.translation.TranslationRepository
import com.example.visiontranslator.infra.service.translate.TranslateService
import javax.inject.Inject

/**
 * edit画面で使用するapi, model操作メソッドの定義
 */
class EditUseCaseImpl
@Inject constructor(
    private val translateService: TranslateService,
    private val translationRepository: TranslationRepository
) : EditUseCase {

    /**
     * GASにAPIリクエストを送り、結果をローカルに保存
     * translationモデルのprimary idを返す
     */
    override suspend fun translate(originalText: String): Long {
        val translatedText = translateService.translateText(originalText, "en", "ja").body()?.text ?: ""
        val translation = Translation(
            originalText = originalText,
            translatedText = translatedText,
        )
        return translationRepository.insertTranslation(translation)
    }

    override fun getTestCaseForOriginalText() = translationRepository.getOriginalTextForTestCase()
}