package kz.magnum.app.data.repository

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.flow
import kz.magnum.app.data.remote.commons.pageMap
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.domain.repository.dynamics.PageProvider

abstract class PageProviderImpl<T, D>(
    private val getPageApi: GetPageApi<T>,
): PageProvider<D> {

    abstract val mapper: (T) -> D

    override suspend fun fetch(query: List<QueryModel>) = flow {
        val page = query.first { it.name == "page" }
        if (page.value as Int > 0) {
            emit(Resource.LoadingMore)
        } else {
            emit(Resource.Loading)
        }
        val sizedQuery: ArrayList<QueryModel> = ArrayList()
        sizedQuery.addAll(query)
        sizedQuery.add(sizeQuery)
        when(val response = getPageApi.getPage(sizedQuery)) {
            is Resource.Success -> {
                val data = response.data.pageMap { it.let(mapper) }
                emit(Resource.Success(data))
            }
            is Resource.Error -> emit(response)
            else -> Unit
        }
    }

    companion object {
        val sizeQuery = QueryModel(name = "size", value = 20)
    }
}