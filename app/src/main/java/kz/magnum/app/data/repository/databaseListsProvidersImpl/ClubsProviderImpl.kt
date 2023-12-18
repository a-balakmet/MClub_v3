package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.utils.self
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.domain.models.BaseEntityHandler

class ClubsProviderImpl(
    getListApi: GetListApi<Club>,
): DatabaseListProviderImpl<Club, Club>(getListApi) {

    override val dbHandler: BaseEntityHandler<Club> = BaseEntityHandler(
        dao = database.clubsDao(),
        replacement = false
    )

    override val mapper = Club::self
    override val fullReplacement: Boolean = true
}