package com.example.visiontranslator.domain.preview

import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.repository.translation.TranslationRepository
import javax.inject.Inject

/**
 * UseCaseは今プロジェクトにおいては画面単位で作成する
 *
 * プレビュー画面PreviewActivityで使用するuseCase
 */
class PreviewUseCaseImpl
@Inject constructor(
    private val translationRepository: TranslationRepository
) : PreviewUseCase {
    /**
     * TranslationモデルをID から取得
     * @return Translationモデル 存在しなければnull
     */
    override suspend fun findTranslationById(id: Long): Translation? =
        translationRepository.findTranslationByID(id)
}