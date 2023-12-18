package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class MonthProgress : ItemApiImp<MonthProgressDto>() {
    override val endPoint: String = "promotion/counterKeeper/clientView"

    override suspend fun backEndItem(id: Int?): Resource<MonthProgressDto> = apiCaller.getRequest(httpClient,endPoint)
}