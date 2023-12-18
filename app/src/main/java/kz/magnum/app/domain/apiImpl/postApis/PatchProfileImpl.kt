package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto

class PatchProfileImpl : PostApiImpl<ProfileUpdateDto, ProfileDto>() {

    override val endPoint: String = "profile"

    override suspend fun fetch(body: ProfileUpdateDto, id: Int?): Resource<ProfileDto> {
        return apiCaller.patchRequest(httpClient = httpClient, endPoint = endPoint, body = body)
    }
}