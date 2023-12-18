package kz.magnum.app.domain.repository

interface TranslationRepository {

    suspend fun getTranslation(key: String) : String
}