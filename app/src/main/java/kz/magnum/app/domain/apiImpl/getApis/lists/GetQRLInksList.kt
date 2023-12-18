package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetQRLInksList : GetListApiImp<QRLink>() {
    override val endPoint: String = "directory/qrLink/list"
    override suspend fun backEndList(): Resource<List<QRLink>> = apiCaller.getRequest(httpClient, endPoint)
}