package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetClubsList: GetListApiImp<Club>() {
    override val endPoint: String = "clubs/list"
    override suspend fun backEndList(): Resource<List<Club>> = apiCaller.getRequest(httpClient, endPoint)
}