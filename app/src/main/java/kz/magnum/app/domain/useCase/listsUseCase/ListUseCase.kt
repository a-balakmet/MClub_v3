package kz.magnum.app.domain.useCase.listsUseCase

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.domain.repository.dynamics.ListProvider

open class ListUseCase<T>(private val repository: ListProvider<T>) {

    suspend operator fun invoke(): Flow<Resource<List<T>>> = repository.fetch()
}