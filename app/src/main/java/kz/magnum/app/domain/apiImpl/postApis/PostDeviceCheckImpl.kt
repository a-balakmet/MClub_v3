package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.profile.DeviceInfoDto

class PostDeviceCheckImpl : PostApiImpl<DeviceInfoDto, Unit>() {

    override val endPoint: String = "profile/devices/check"

    override suspend fun fetch(body: DeviceInfoDto, id: Int?): Resource<Unit> {
        return apiCaller.postRequest(httpClient = httpClient, endPoint = endPoint, body = body)
    }
}