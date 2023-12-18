package kz.magnum.app.domain.apiImpl.getApis.pages

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.campaigns.CampaignDto
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.domain.apiImpl.getApis.GetPageApiImp

class GetCampaigns : GetPageApiImp<CampaignDto>() {

    override val endPoint: String = "campaigns/page"

    override suspend fun backEndPage(query: List<QueryModel>): Resource<PageableDto<CampaignDto>> {
        return apiCaller.getRequest(httpClient, endPoint, query)
    }

    override suspend fun backEndPages(): Resource<List<CampaignDto>> {
        return apiCaller.getPages(httpClient, endPoint)
    }

}