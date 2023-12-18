package kz.magnum.app.ui.components.bonusCards

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.ui.builders.baseViewModels.DatabaseListViewModel

class BonusCardsViewModel(
    handle: SavedStateHandle,
    useCase: DatabaseListUseCase<BonusCard>
) : DatabaseListViewModel<BonusCard>(handle, useCase) {

    override val forceLoad: Boolean = true
    override val savedState: String = "cards_loaded"
    override var dbFlowProvider: Flow<List<BonusCard>> = database.cardsDao().emitCards()

    init {
        onInit()
    }
}