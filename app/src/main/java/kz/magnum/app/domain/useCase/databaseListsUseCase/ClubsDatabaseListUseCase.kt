package kz.magnum.app.domain.useCase.databaseListsUseCase

import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class ClubsDatabaseListUseCase(repository: DatabaseListProvider<Club>) : DatabaseListUseCase<Club>(repository)
