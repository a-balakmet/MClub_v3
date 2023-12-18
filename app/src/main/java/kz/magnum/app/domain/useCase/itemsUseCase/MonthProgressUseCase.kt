package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class MonthProgressUseCase(repository: ItemProvider<MonthProgressDto>): ItemUseCase<MonthProgressDto>(repository)