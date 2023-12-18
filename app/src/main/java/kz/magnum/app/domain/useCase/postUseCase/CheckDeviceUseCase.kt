package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.data.remote.profile.DeviceInfoDto
import kz.magnum.app.domain.repository.dynamics.PostProvider

class CheckDeviceUseCase(repository: PostProvider<DeviceInfoDto, Unit>): PostUseCase<DeviceInfoDto, Unit, Unit>(repository)
