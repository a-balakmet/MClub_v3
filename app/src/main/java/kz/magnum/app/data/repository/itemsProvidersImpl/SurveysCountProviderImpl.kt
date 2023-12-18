package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.data.repository.ItemProviderImpl

class SurveysCountProviderImpl(itemApi: ItemApi<CountDto>) : ItemProviderImpl<CountDto, CountDto>(itemApi) {
    override val mapper = CountDto::self
}