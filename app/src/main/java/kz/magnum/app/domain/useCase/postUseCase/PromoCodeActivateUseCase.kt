package kz.magnum.app.domain.useCase.postUseCase

import kz.magnum.app.domain.repository.dynamics.PostProvider

class PromoCodeActivateUseCase(repository: PostProvider<Map<String,String>, Unit>): PostUseCase<Map<String,String>, Unit, Unit>(repository)
