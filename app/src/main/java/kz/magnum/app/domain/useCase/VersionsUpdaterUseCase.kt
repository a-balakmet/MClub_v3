package kz.magnum.app.domain.useCase

import aab.lib.commons.data.dataStore.DataStoreKeys
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Version
import kz.magnum.app.domain.repository.VersionsSavingRepository

class VersionsUpdaterUseCase(
    private val versionsSavingRepository: VersionsSavingRepository,
    private val ioDispatcher: CoroutineScope,
    private val dataStore: DataStoreRepository,
    private val database: MagnumClubDatabase
) {

    suspend fun getVersions(fullUpdate: Boolean, localeChanged: Boolean) {

        var job: Job? = null
        if (fullUpdate) {
            job = versionsSavingRepository.clearDatabase()
        }

        if (localeChanged) {
            job = ioDispatcher.launch {
                for (item in localeDependedDto) {
                    database.versionsDao().getItemByName(item)?.let {
                        database.versionsDao().delete(it)
                    }
                }
                database.cityDao().deleteAll()
                database.shopDao().deleteAll()
                database.campaignsDao().deleteAll()
                database.clubsDao().deleteAll()
                database.discountsDao().deleteAll()
                database.storiesDao().deleteAll()
                database.storiesScreensDao().deleteAll()
                database.miniGamesDao().deleteAll()
            }
        }
        job?.let {
            it.invokeOnCompletion {
                ioDispatcher.launch {
                    loadData()
                }
            }
        } ?: run {
            loadData()
        }
    }

    private suspend fun loadData() {

        val token = dataStore.readPreference(key = DataStoreKeys.accessToken, defaultValue = "")
        val listToSave: ArrayList<Version> = ArrayList()

        fun addVersionItem(version: Version) {
            if (token == "") {
                if (nonAuthorizedDto.contains(version.name)) {
                    listToSave.add(version)
                }
            } else listToSave.add(version)
        }

        versionsSavingRepository.loadVersion().collect {
            when (it) {
                is Resource.Success -> {
                    it.data.forEach { ver ->
                        val localVersion = database.versionsDao().getItemByName(ver.name)
                    localVersion?.let { local ->
                        if (local.date.isBefore(ver.date)) {
                            addVersionItem(ver)
                        }
                    } ?: run {
                        addVersionItem(ver)
                    }
                    }
                }

                else -> Unit
            }
        }

        listToSave.forEach {
            when (it.name) {
                translation -> versionsSavingRepository.loadTranslations(version = it)
                city -> versionsSavingRepository.loadCities(version = it)
                actor -> versionsSavingRepository.loadShops(version = it)
                campaign -> versionsSavingRepository.loadCampaigns(version = it)
                club -> versionsSavingRepository.loadClubs(version = it)
                discount -> versionsSavingRepository.loadDiscounts(version = it)
                stories -> versionsSavingRepository.loadStories(version = it)
                games -> versionsSavingRepository.loadGames(version = it)
                qrLinks -> versionsSavingRepository.loadQRLinks(version = it)
                shopping_list_icon -> versionsSavingRepository.loadShoppingIcons(version = it)
            }
        }
    }

    companion object {
        const val translation = "translation"
        const val city = "city"
        const val actor = "actor"
        const val campaign = "campaign"
        const val discount = "discount"
        const val club = "club"
        const val stories = "stories"
        const val games = "game"
        const val qrLinks = "qr_link"
        const val shopping_list_icon = "shoppingIcons"
        val nonAuthorizedDto = listOf(translation, city, actor, games, qrLinks)
        val localeDependedDto = listOf(city, actor, campaign, discount, club, stories, games)
    }
}