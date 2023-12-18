package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.discounts.DiscountsDataDto
import kz.magnum.app.data.repository.ItemProviderImpl

class DiscountsDataProviderImpl(itemApi: ItemApi<DiscountsDataDto>): ItemProviderImpl<DiscountsDataDto, DiscountsDataDto>(itemApi) {
    override val mapper = DiscountsDataDto::self
}