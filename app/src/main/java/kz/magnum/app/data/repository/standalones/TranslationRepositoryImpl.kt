package kz.magnum.app.data.repository.standalones

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.extensions.toDataClass
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Translation
import kz.magnum.app.domain.repository.TranslationRepository

class TranslationRepositoryImpl(
    dataStore: DataStoreRepository,
    private val database: MagnumClubDatabase,
) : TranslationRepository {

    private var locale = "ru"
    private var fileTranslations: ArrayList<Translation> = ArrayList()

    init {
        val context = dataStore.provideContext()
        val jsonString = context.assets.open("langs.json")
            .bufferedReader()
            .use { it.readText() }

        jsonString.toDataClass<List<Translation>>()?.let {
            fileTranslations.addAll(it)
        }
    }

    override suspend fun getTranslation(key: String): String {
        val translation = database.translationDao().getTranslation(key)
        translation?.let {
            return when (locale) {
                "ru" -> it.ru
                "kk" -> it.kk
                else -> "unknown locale"
            }
        } ?: run {
            return getFileTranslation(key)
        }
    }

    private fun getFileTranslation(key: String): String {
        val fileTranslation = fileTranslations.firstOrNull { inFile -> inFile.name == key }
        return if (fileTranslation != null) {
            when (locale) {
                "ru" -> fileTranslation.ru
                "kk" -> fileTranslation.kk
                else -> "unknown locale"
            }
        } else {
            "unknown translation: $key"
        }
    }
}