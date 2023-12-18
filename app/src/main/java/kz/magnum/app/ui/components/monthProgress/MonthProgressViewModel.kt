package kz.magnum.app.ui.components.monthProgress

import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class MonthProgressViewModel(useCase: ItemUseCase<MonthProgressDto>) : ItemViewModel<MonthProgressDto>(useCase) {

    override val savedState: String = "month_progress"

    init {
        onInit()
    }
}