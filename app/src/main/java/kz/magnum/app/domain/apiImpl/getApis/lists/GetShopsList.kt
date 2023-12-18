package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.shops.ShopDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetShopsList : GetListApiImp<ShopDto>() {
    override val endPoint: String = "directory/actors"
    override suspend fun backEndList(): Resource<List<ShopDto>> = apiCaller.getRequest(httpClient, endPoint)
}