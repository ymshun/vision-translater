package com.example.visiontranslator.domain.home

import com.example.visiontranslator.infra.model.translation.Translation
import com.example.visiontranslator.infra.repository.translation.TranslationRepository
import javax.inject.Inject

class HomeUseCaseImpl
@Inject constructor(
    private val translationsRepository: TranslationRepository
) : HomeUseCase {
    override suspend fun getAllTranslations(): List<Translation> =
        translationsRepository.getAllTranslations()

}