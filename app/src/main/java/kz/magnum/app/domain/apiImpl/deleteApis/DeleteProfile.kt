package kz.magnum.app.domain.apiImpl.deleteApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.domain.apiImpl.ItemApiImp

class DeleteProfile: ItemApiImp<Unit>() {

    override val endPoint: String = "profile"
    override suspend fun backEndItem(id: Int?): Resource<Unit> = apiCaller.deleteRequest(httpClient, endPoint)

}