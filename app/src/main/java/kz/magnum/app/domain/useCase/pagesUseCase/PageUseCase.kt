package kz.magnum.app.domain.useCase.pagesUseCase

import aab.lib.commons.domain.models.QueryModel
import kz.magnum.app.domain.repository.dynamics.PageProvider

open class PageUseCase<T>(private val repository: PageProvider<T>) {

    suspend operator fun invoke(query: List<QueryModel>) = repository.fetch(query)
}