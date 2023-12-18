package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto

class PostBonusCardBindImpl : PostApiImpl<BonusCardBind, BonusCardDto>() {

    override val endPoint: String = "accounting/cards"

    override suspend fun fetch(body: BonusCardBind, id: Int?): Resource<BonusCardDto> {
        val suffix = if (body.cardNumber == null) "/generation" else "/bind"
        return apiCaller.postRequest(httpClient = httpClient, endPoint = "${endPoint}$suffix", body = body)
    }
}