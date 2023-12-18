package kz.magnum.app.domain.useCase.listsUseCase

import kz.magnum.app.domain.models.Burning
import kz.magnum.app.domain.repository.dynamics.ListProvider

class BurningsListUseCase(repository: ListProvider<Burning>) : ListUseCase<Burning>(repository)