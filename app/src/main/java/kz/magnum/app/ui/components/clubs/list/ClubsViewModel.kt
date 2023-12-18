package kz.magnum.app.ui.components.clubs.list

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.ui.builders.baseViewModels.DatabaseListViewModel

class ClubsViewModel(
    handle: SavedStateHandle,
    useCase: DatabaseListUseCase<Club>
) : DatabaseListViewModel<Club>(handle, useCase) {

    override val forceLoad: Boolean = false
    override val savedState: String = "clubs_loaded"
    override var dbFlowProvider: Flow<List<Club>> = database.clubsDao().emitClubs()

    init {
        onInit()
    }
}