package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.repository.ItemProviderImpl

class MonthProgressProviderImpl(itemApi: ItemApi<MonthProgressDto>) : ItemProviderImpl<MonthProgressDto, MonthProgressDto>(itemApi) {
    override val mapper = MonthProgressDto::self
}