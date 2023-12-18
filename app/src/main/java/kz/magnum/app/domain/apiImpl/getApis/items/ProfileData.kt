package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class ProfileData: ItemApiImp<ProfileDto>() {

    override val endPoint: String = "profile"
    override suspend fun backEndItem(id: Int?): Resource<ProfileDto> = apiCaller.getRequest(httpClient, endPoint)

}