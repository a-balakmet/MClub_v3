package kz.magnum.app.ui.components.campaigns.list

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.ui.builders.baseViewModels.DatabaseListViewModel

class CampaignsViewModel(
    handle: SavedStateHandle,
    useCase: DatabaseListUseCase<Campaign>
) : DatabaseListViewModel<Campaign>(handle, useCase) {

    override val forceLoad: Boolean = false
    override val savedState: String = "campaigns_loaded"
    override var dbFlowProvider: Flow<List<Campaign>> = database.campaignsDao().emitCampaigns()

    init {
        onInit()
    }
}