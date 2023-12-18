package kz.magnum.app.ui.components.buttonsGrid

import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel

class ButtonsViewModel(useCase: ListUseCase<NavigationItem>) : ListViewModel<NavigationItem>(useCase) {

    override val savedState: String = "nav_buttons"

    init {
        onInit()
    }
}