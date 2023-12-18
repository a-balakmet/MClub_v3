package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.domain.repository.dynamics.ItemProvider

class DeleteProfileUseCase(repository: ItemProvider<Unit>): ItemUseCase<Unit>(repository)