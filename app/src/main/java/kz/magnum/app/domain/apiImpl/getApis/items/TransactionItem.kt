package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.accounting.TransactionFullDataDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class TransactionItem : ItemApiImp<TransactionFullDataDto>() {

    override val endPoint: String = "accounting/transactions/"
    override suspend fun backEndItem(id: Int?): Resource<TransactionFullDataDto> = apiCaller.getRequestWithSegmentedItemId(
        httpClient = httpClient,
        endPoint = endPoint,
        pathParam = "${id!!}/details",
        query = listOf(
            QueryModel("not_round", true)
        )
    )
}