package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource

class EnterClubImpl : PostApiImpl<Map<String,String>, Unit>() {

    override val endPoint: String = "clubs"

    override suspend fun fetch(body: Map<String,String>, id: Int?): Resource<Unit> {
        return apiCaller.postRequest(httpClient = httpClient, endPoint = endPoint, body = body, pathParam = "membership", idPathParam = id)
    }
}