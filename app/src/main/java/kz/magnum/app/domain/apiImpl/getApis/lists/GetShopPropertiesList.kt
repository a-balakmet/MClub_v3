package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.shops.ShopPropertyDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetShopPropertiesList : GetListApiImp<ShopPropertyDto>() {
    override val endPoint: String = "directory/actors/additionalProp/list"
    override suspend fun backEndList(): Resource<List<ShopPropertyDto>> = apiCaller.getRequest(httpClient, endPoint)
}