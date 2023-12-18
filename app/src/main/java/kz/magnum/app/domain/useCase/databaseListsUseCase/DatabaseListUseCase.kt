package kz.magnum.app.domain.useCase.databaseListsUseCase

import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

open class DatabaseListUseCase<T: BaseEntity>(private val repository: DatabaseListProvider<T>) {

    suspend operator fun invoke() = repository.fetch()
}