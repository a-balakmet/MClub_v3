package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.promotions.StickerExchangeDto

class PostStickerExchangeImpl : PostApiImpl<StickerExchangeDto, Unit>() {

    override val endPoint: String = "promotion/achievementsClient/stickersExchange"

    override suspend fun fetch(body: StickerExchangeDto, id: Int?): Resource<Unit> {
        return apiCaller.postRequest(httpClient = httpClient, endPoint = endPoint, body = body)
    }
}