package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.AboutDocsDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetAboutDocsList : GetListApiImp<AboutDocsDto>() {
    override val endPoint: String = "directory/content/agreements/list"
    override suspend fun backEndList(): Resource<List<AboutDocsDto>> = apiCaller.getRequest(httpClient, endPoint)
}