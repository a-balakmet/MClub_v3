package kz.magnum.app.ui.components.transactions.item

import kz.magnum.app.domain.models.transactions.TransactionContent
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class TransactionViewModel(useCase: ItemUseCase<TransactionContent>) : ItemViewModel<TransactionContent>(useCase) {

    override val savedState: String = "one_transaction"

    init {
        onInit()
    }
}