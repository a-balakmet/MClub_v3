package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.domain.repository.dynamics.PostProvider

class BonusCardBindUseCase(repository: PostProvider<BonusCardBind, BonusCardDto>): PostUseCase<BonusCardBind, BonusCardDto, BonusCard>(repository)
