package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class SurveysCount : ItemApiImp<CountDto>() {
    override val endPoint: String = "surveys/count"

    override suspend fun backEndItem(id: Int?): Resource<CountDto> = apiCaller.getRequest(httpClient, endPoint)
}