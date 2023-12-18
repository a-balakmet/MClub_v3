package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.stories.StoryDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetStoriesList : GetListApiImp<StoryDto>() {
    override val endPoint: String = "stories"
    override suspend fun backEndList(): Resource<List<StoryDto>> = apiCaller.getRequest(httpClient, endPoint)
}