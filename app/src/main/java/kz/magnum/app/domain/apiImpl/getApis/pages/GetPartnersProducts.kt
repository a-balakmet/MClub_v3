package kz.magnum.app.domain.apiImpl.getApis.pages

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.domain.apiImpl.getApis.GetPageApiImp

class GetPartnersProducts : GetPageApiImp<PartnerProductDto>() {

    override val endPoint: String = "directory/productListItem/page"

    override suspend fun backEndPage(query: List<QueryModel>): Resource<PageableDto<PartnerProductDto>> {
        return apiCaller.getRequest(httpClient, endPoint, query)
    }

    override suspend fun backEndPages(): Resource<List<PartnerProductDto>> {
        return apiCaller.getPages(httpClient, endPoint)
    }

}