package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class PromoCode : ItemApiImp<PromoCodeDto>() {

    override val endPoint: String = "promoCode/referral"
    override suspend fun backEndItem(id: Int?): Resource<PromoCodeDto> = apiCaller.getRequestWithItemId(httpClient, endPoint, null)

}