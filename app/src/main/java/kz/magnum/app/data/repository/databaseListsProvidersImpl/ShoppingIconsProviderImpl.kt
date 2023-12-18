package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.domain.models.BaseEntityHandler

class ShoppingIconsProviderImpl(getListApi: GetListApi<ShoppingIcons>) : DatabaseListProviderImpl<ShoppingIcons, ShoppingIcons>(getListApi) {

    override val dbHandler: BaseEntityHandler<ShoppingIcons> = BaseEntityHandler(
        dao = database.shoppingIconsDao(),
        replacement = false
    )

    override val mapper = ShoppingIcons::self
    override val fullReplacement: Boolean = true
}