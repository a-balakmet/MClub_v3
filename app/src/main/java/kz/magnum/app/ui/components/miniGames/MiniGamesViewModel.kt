package kz.magnum.app.ui.components.miniGames

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.ui.builders.baseViewModels.DatabaseListViewModel

class MiniGamesViewModel(
    handle: SavedStateHandle,
    useCase: DatabaseListUseCase<MiniGame>
) : DatabaseListViewModel<MiniGame>(handle, useCase) {

    override val forceLoad: Boolean = false
    override val savedState: String = "games_loaded"
    override var dbFlowProvider: Flow<List<MiniGame>> = database.miniGamesDao().emitMiniGames()

    init {
        onInit()
    }
}