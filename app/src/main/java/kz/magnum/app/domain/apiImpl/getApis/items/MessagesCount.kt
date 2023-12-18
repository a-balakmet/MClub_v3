package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class MessagesCount : ItemApiImp<CountDto>() {
    override val endPoint: String = "messages/unreadCount"

    override suspend fun backEndItem(id: Int?): Resource<CountDto> = apiCaller.getRequest(httpClient, endPoint)
}