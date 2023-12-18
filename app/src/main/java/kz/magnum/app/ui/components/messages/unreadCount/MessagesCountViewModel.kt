package kz.magnum.app.ui.components.messages.unreadCount

import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class MessagesCountViewModel(
    useCase: ItemUseCase<CountDto>) : ItemViewModel<CountDto>(useCase) {

    override val savedState: String = "messages_count"

    init {
        onInit()
    }
}