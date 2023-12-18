package kz.magnum.app.data.repository.pageProviderImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.repository.PageProviderImpl

class PartnersProductsProviderImpl(getPageApi: GetPageApi<PartnerProductDto>): PageProviderImpl<PartnerProductDto, PartnerProductDto>(getPageApi) {
    override val mapper = PartnerProductDto::self
}