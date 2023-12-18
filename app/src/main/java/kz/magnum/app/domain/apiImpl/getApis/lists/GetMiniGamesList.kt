package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.MiniGameDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetMiniGamesList : GetListApiImp<MiniGameDto>() {
    override val endPoint: String = "directory/game/list"
    override suspend fun backEndList(): Resource<List<MiniGameDto>> = apiCaller.getRequest(httpClient, endPoint)
}