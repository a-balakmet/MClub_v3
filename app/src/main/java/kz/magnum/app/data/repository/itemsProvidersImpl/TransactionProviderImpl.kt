package kz.magnum.app.data.repository.itemsProvidersImpl

import kz.magnum.app.data.remote.accounting.TransactionFullDataDto
import kz.magnum.app.data.remote.accounting.toTransactionContent
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.repository.ItemProviderImpl
import kz.magnum.app.domain.models.transactions.TransactionContent

class TransactionProviderImpl(itemApi: ItemApi<TransactionFullDataDto>) : ItemProviderImpl<TransactionFullDataDto, TransactionContent>(itemApi) {
    override val mapper = TransactionFullDataDto::toTransactionContent
}