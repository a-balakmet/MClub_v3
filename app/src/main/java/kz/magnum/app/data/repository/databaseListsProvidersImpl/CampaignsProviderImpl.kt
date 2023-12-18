package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.remote.campaigns.CampaignDto
import kz.magnum.app.data.remote.campaigns.toCampaign
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class CampaignsProviderImpl(
    private val ioDispatcher: CoroutineScope,
    private val getPagesApi: GetPageApi<CampaignDto>,
    private val database: MagnumClubDatabase,
) : DatabaseListProvider<Campaign> {

    override suspend fun fetch() {
        val resource = getPagesApi.getAllPages()
        if (resource is Resource.Success) {
            if (resource.data.isNotEmpty()) {
                val list = resource.data.map { it.toCampaign() }
                ioDispatcher.launch {
                    database.campaignsDao().deleteAll()
                    database.campaignsDao().insertList(list)
                }
            }
        }
    }
}