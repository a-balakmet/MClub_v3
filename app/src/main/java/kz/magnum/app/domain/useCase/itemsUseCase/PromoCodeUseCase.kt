package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class PromoCodeUseCase(repository: ItemProvider<PromoCodeDto>): ItemUseCase<PromoCodeDto>(repository)