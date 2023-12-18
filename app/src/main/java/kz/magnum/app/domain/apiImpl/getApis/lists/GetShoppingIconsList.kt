package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetShoppingIconsList : GetListApiImp<ShoppingIcons>() {
    override val endPoint: String = "profile/shoppingListIcons/list"
    override suspend fun backEndList(): Resource<List<ShoppingIcons>> = apiCaller.getRequest(httpClient, endPoint)
}