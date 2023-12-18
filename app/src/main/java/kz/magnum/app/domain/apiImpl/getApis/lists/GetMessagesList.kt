package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.dto.MessageDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetMessagesList: GetListApiImp<MessageDto>() {
    override val endPoint: String = "messages"
    override suspend fun backEndList(): Resource<List<MessageDto>> = apiCaller.getRequest(httpClient, endPoint)
}