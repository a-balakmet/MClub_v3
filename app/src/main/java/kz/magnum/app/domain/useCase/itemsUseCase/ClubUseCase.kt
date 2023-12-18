package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class ClubUseCase(repository: ItemProvider<Club>): ItemUseCase<Club>(repository)