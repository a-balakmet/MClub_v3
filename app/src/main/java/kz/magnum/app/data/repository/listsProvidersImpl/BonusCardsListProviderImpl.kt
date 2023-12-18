package kz.magnum.app.data.repository.listsProvidersImpl

import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.accounting.toBonusCard
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.repository.ListProviderImpl
import kz.magnum.app.data.room.entities.BonusCard

class BonusCardsListProviderImpl(getListApi: GetListApi<BonusCardDto>): ListProviderImpl<BonusCardDto, BonusCard>(getListApi) {
    override val mapper = BonusCardDto::toBonusCard
}