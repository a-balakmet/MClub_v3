package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class SurveysCountUseCase(repository: ItemProvider<CountDto>): ItemUseCase<CountDto>(repository)