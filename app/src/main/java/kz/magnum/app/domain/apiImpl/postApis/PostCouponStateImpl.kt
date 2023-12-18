package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource

class PostCouponStateImpl : PostApiImpl<Map<String,Int>, Unit>() {

    override val endPoint: String = "coupon"

    override suspend fun fetch(body: Map<String,Int>, id: Int?): Resource<Unit> {
        val suffix = when (id) {
            1 -> "/activate"
            2 -> "/deactivate"
            else -> ""
        }
        return apiCaller.postRequest(httpClient = httpClient, endPoint = "$endPoint$suffix", body = body)
    }
}