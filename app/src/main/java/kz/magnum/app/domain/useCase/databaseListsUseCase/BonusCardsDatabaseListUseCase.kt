package kz.magnum.app.domain.useCase.databaseListsUseCase

import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class BonusCardsDatabaseListUseCase(repository: DatabaseListProvider<BonusCard>) : DatabaseListUseCase<BonusCard>(repository)
