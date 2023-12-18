package kz.magnum.app.data.repository.listsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.repository.ListProviderImpl

class QRLinksListProviderImpl(getListApi: GetListApi<QRLink>): ListProviderImpl<QRLink, QRLink>(getListApi) {
    override val mapper = QRLink::self
}