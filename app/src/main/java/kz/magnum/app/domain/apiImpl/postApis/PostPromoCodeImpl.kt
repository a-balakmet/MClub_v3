package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource

class PostPromoCodeImpl : PostApiImpl<Map<String,String>, Unit>() {

    override val endPoint: String = "promoCode/activate"

    override suspend fun fetch(body: Map<String,String>, id: Int?): Resource<Unit> {
        return apiCaller.postRequest(httpClient = httpClient, endPoint = endPoint, body = body)
    }
}