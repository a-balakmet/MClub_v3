package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.domain.repository.dynamics.PostProvider

class CouponStateUseCase(repository: PostProvider<Map<String,Int>, Unit>): PostUseCase<Map<String,Int>, Unit, Unit>(repository)
