package kz.magnum.app.domain.apiImpl.deleteApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.domain.apiImpl.ItemApiImp

class ExitClub: ItemApiImp<Unit>() {

    override val endPoint: String = "clubs"
    override suspend fun backEndItem(id: Int?): Resource<Unit> = apiCaller.deleteRequest(httpClient, endPoint, pathParam = "membership", idPathParam = id)

}