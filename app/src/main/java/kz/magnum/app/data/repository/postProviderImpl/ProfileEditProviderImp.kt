package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto
import kz.magnum.app.data.repository.PostProviderImpl

class ProfileEditProviderImp(postApi: PostApi<ProfileUpdateDto, ProfileDto>): PostProviderImpl<ProfileUpdateDto, ProfileDto>(postApi)