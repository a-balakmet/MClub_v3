package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.domain.models.BaseEntityHandler

class QRLinksProviderImpl(getListApi: GetListApi<QRLink>) : DatabaseListProviderImpl<QRLink, QRLink>(getListApi) {

    override val dbHandler: BaseEntityHandler<QRLink> = BaseEntityHandler(
        dao = database.qrLinksDao(),
        replacement = false
    )

    override val mapper = QRLink::self
    override val fullReplacement: Boolean = true
}