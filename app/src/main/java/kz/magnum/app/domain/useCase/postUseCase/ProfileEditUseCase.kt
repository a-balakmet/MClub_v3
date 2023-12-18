package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.repository.dynamics.PostProvider

class ProfileEditUseCase(repository: PostProvider<ProfileUpdateDto, ProfileDto>): PostUseCase<ProfileUpdateDto, ProfileDto, User>(repository)
