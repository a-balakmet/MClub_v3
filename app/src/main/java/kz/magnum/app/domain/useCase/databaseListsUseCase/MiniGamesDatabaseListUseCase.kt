package kz.magnum.app.domain.useCase.databaseListsUseCase

import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class MiniGamesDatabaseListUseCase(repository: DatabaseListProvider<MiniGame>) : DatabaseListUseCase<MiniGame>(repository)
