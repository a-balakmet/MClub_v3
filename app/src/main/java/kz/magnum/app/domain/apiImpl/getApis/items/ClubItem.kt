package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.domain.apiImpl.ItemApiImp

class ClubItem : ItemApiImp<Club>() {

    override val endPoint: String = "clubs/"
    override suspend fun backEndItem(id: Int?): Resource<Club> = apiCaller.getRequestWithItemId(httpClient, endPoint, id!!)

}