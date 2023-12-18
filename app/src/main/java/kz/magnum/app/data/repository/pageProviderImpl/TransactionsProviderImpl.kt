package kz.magnum.app.data.repository.pageProviderImpl

import kz.magnum.app.data.remote.accounting.TransactionDto
import kz.magnum.app.data.remote.accounting.toTransaction
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.repository.PageProviderImpl
import kz.magnum.app.domain.models.transactions.Transaction

class TransactionsProviderImpl(getPageApi: GetPageApi<TransactionDto>): PageProviderImpl<TransactionDto, Transaction>(getPageApi) {
    override val mapper = TransactionDto::toTransaction
}