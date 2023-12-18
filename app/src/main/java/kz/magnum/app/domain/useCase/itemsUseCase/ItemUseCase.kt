package kz.magnum.app.domain.useCase.itemsUseCase

import kz.magnum.app.domain.repository.dynamics.ItemProvider

open class ItemUseCase<T>(private val repository: ItemProvider<T>) {

    suspend operator fun invoke(id: Int?) = repository.fetch(id)
}