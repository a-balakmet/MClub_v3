package kz.magnum.app.domain.useCase.databaseListsUseCase

import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class CampaignsDatabaseListUseCase(repository: DatabaseListProvider<Campaign>) : DatabaseListUseCase<Campaign>(repository)
