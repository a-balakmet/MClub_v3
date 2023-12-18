package kz.magnum.app.data.repository.databaseListsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.shops.ShopPropertyDto
import kz.magnum.app.data.remote.directory.shops.toShopProperty
import kz.magnum.app.data.room.entities.ShopProperty
import kz.magnum.app.domain.models.BaseEntityHandler

class ShopPropertiesProviderImpl(getListApi: GetListApi<ShopPropertyDto>) : DatabaseListProviderImpl<ShopPropertyDto, ShopProperty>(getListApi) {

    override val dbHandler: BaseEntityHandler<ShopProperty> = BaseEntityHandler(
        dao = database.shopPropertiesDao(),
        replacement = false
    )

    override val mapper = ShopPropertyDto::toShopProperty
    override val fullReplacement: Boolean = true
}