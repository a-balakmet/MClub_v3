package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.repository.ItemProviderImpl

class CampaignProviderImpl(itemApi: ItemApi<CampaignDataDto>) : ItemProviderImpl<CampaignDataDto, CampaignDataDto>(itemApi) {
    override val mapper = CampaignDataDto::self
}