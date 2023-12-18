package kz.magnum.app.data.repository.databaseListsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.shops.ShopTypeDto
import kz.magnum.app.data.remote.directory.shops.toShopType
import kz.magnum.app.data.room.entities.ShopType
import kz.magnum.app.domain.models.BaseEntityHandler

class ShopTypesProviderImpl(getListApi: GetListApi<ShopTypeDto>) : DatabaseListProviderImpl<ShopTypeDto, ShopType>(getListApi) {

    override val dbHandler: BaseEntityHandler<ShopType> = BaseEntityHandler(
        dao = database.shopTypeDao(),
        replacement = false
    )

    override val mapper = ShopTypeDto::toShopType
    override val fullReplacement: Boolean = true
}