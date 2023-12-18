package kz.magnum.app.data.repository.listsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.versions.VersionDto
import kz.magnum.app.data.remote.versions.toVersion
import kz.magnum.app.data.repository.ListProviderImpl
import kz.magnum.app.data.room.entities.Version

class VersionsListProviderImpl(getListApi: GetListApi<VersionDto>): ListProviderImpl<VersionDto, Version>(getListApi) {
    override val mapper = VersionDto::toVersion
}