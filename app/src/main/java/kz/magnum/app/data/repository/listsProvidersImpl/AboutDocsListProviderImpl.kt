package kz.magnum.app.data.repository.listsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.AboutDocsDto
import kz.magnum.app.data.remote.directory.toAboutDoc
import kz.magnum.app.data.repository.ListProviderImpl
import kz.magnum.app.domain.models.AboutDoc

class AboutDocsListProviderImpl(getListApi: GetListApi<AboutDocsDto>): ListProviderImpl<AboutDocsDto, AboutDoc>(getListApi) {
    override val mapper = AboutDocsDto::toAboutDoc
}