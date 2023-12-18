package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class ProfileUseCase(repository: ItemProvider<User>): ItemUseCase<User>(repository)