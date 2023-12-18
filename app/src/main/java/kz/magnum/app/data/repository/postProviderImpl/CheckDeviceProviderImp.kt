package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.profile.DeviceInfoDto
import kz.magnum.app.data.repository.PostProviderImpl

class CheckDeviceProviderImp(postApi: PostApi<DeviceInfoDto, Unit>): PostProviderImpl<DeviceInfoDto, Unit>(postApi)
