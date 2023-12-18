package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.CityDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetCitiesList : GetListApiImp<CityDto>() {
    override val endPoint: String = "directory/cities"
    override suspend fun backEndList(): Resource<List<CityDto>> = apiCaller.getRequest(httpClient, endPoint)
}