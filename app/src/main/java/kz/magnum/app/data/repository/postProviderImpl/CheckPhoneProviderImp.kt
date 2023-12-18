package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.data.repository.PostProviderImpl

class CheckPhoneProviderImp(postApi: PostApi<PhoneDto, Unit>): PostProviderImpl<PhoneDto, Unit>(postApi)
