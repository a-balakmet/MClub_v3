package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource

class PostIINImpl : PostApiImpl<Map<String,String>, Unit>() {

    override val endPoint: String = "profile/property/iin"

    override suspend fun fetch(body: Map<String,String>, id: Int?): Resource<Unit> {
        return apiCaller.postRequest(httpClient = httpClient, endPoint = endPoint, body = body)
    }
}