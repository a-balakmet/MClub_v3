package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.data.remote.promotions.StickerExchangeDto
import kz.magnum.app.domain.repository.dynamics.PostProvider

class StickersExchangeUseCase(repository: PostProvider<StickerExchangeDto, Unit>): PostUseCase<StickerExchangeDto, Unit, Unit>(repository)
