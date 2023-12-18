package kz.magnum.app.domain.useCase

import kz.magnum.app.domain.repository.TranslationRepository

class TranslationUseCase(private val repository: TranslationRepository) {

    suspend operator fun invoke(key: String) = repository.getTranslation(key)
}