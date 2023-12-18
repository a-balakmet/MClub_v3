package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.data.repository.ItemProviderImpl

class PromoCodeProviderImpl(itemApi: ItemApi<PromoCodeDto>) : ItemProviderImpl<PromoCodeDto, PromoCodeDto>(itemApi) {
    override val mapper = PromoCodeDto::self
}