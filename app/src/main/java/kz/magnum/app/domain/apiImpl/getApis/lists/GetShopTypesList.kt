package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.shops.ShopTypeDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetShopTypesList : GetListApiImp<ShopTypeDto>() {
    override val endPoint: String = "directory/actors/type/list"
    override suspend fun backEndList(): Resource<List<ShopTypeDto>> = apiCaller.getRequest(httpClient, endPoint)
}