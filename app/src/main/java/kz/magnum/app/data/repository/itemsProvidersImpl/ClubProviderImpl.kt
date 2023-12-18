package kz.magnum.app.data.repository.itemsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.repository.ItemProviderImpl

class ClubProviderImpl(itemApi: ItemApi<Club>) : ItemProviderImpl<Club, Club>(itemApi) {
    override val mapper = Club::self
}