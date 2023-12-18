package kz.magnum.app.data.repository.databaseListsProvidersImpl

import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.accounting.toBonusCard
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.domain.models.BaseEntityHandler

class BonusCardsProviderImpl(
    getListApi: GetListApi<BonusCardDto>,
) : DatabaseListProviderImpl<BonusCardDto, BonusCard>(getListApi) {


    override val dbHandler: BaseEntityHandler<BonusCard> = BaseEntityHandler(
        dao = database.cardsDao(),
        replacement = true
    )

    override val mapper = BonusCardDto::toBonusCard
    override val fullReplacement: Boolean = false
}