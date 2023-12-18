package kz.magnum.app.domain.apiImpl.getApis.pages

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.accounting.TransactionDto
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.domain.apiImpl.getApis.GetPageApiImp

class GetTransactions : GetPageApiImp<TransactionDto>() {

    override val endPoint: String = "accounting/transactions/page"

    override suspend fun backEndPage(query: List<QueryModel>): Resource<PageableDto<TransactionDto>> {
        return apiCaller.getRequest(httpClient, endPoint, query)
    }

    override suspend fun backEndPages(): Resource<List<TransactionDto>> {
        return apiCaller.getPages(httpClient, endPoint)
    }

}