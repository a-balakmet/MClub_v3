package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.versions.VersionDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetVersionsList : GetListApiImp<VersionDto>() {
    override val endPoint: String = "versions/list"
    override suspend fun backEndList(): Resource<List<VersionDto>> = apiCaller.getRequest(httpClient, endPoint)
}