package kz.magnum.app.domain.repository

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.entities.Version

interface VersionsSavingRepository {
    suspend fun loadCampaigns(version: Version)
    suspend fun loadCities(version: Version)
    suspend fun loadClubs(version: Version)
    suspend fun loadDiscounts(version: Version)
    suspend fun loadGames(version: Version)
    suspend fun loadQRLinks(version: Version)
    suspend fun loadShoppingIcons(version: Version)
    suspend fun loadShopProperties()
    suspend fun loadShops(version: Version)
    suspend fun loadShopTypes()
    suspend fun loadStories(version: Version)
    suspend fun loadTranslations(version: Version)
    suspend fun loadVersion(): Flow<Resource<List<Version>>>
    suspend fun clearDatabase(): Job
}