package kz.magnum.app.domain.useCase.listsUseCase

import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.repository.dynamics.ListProvider

class MainButtonsGreedUseCase(repository: ListProvider<NavigationItem>) : ListUseCase<NavigationItem>(repository)