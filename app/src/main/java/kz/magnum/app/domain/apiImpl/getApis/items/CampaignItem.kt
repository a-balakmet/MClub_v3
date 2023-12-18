package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class CampaignItem : ItemApiImp<CampaignDataDto>() {

    override val endPoint: String = "campaigns/"
    override suspend fun backEndItem(id: Int?): Resource<CampaignDataDto> = apiCaller.getRequestWithItemId(httpClient, endPoint, id!!)

}