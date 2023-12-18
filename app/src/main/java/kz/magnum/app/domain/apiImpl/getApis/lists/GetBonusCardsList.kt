package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetBonusCardsList : GetListApiImp<BonusCardDto>() {

    override val endPoint: String = "accounting/cards"
    override suspend fun backEndList(): Resource<List<BonusCardDto>> = apiCaller.getRequest(httpClient, endPoint)
}