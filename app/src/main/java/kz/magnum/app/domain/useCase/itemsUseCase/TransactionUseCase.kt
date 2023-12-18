package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.domain.models.transactions.TransactionContent
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class TransactionUseCase(repository: ItemProvider<TransactionContent>): ItemUseCase<TransactionContent>(repository)