package kz.magnum.app.data.repository

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.flow
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.domain.repository.dynamics.ItemProvider

abstract class ItemProviderImpl<T, D>(
    private val getApi: ItemApi<T>,
): ItemProvider<D> {

    abstract val mapper: (T) -> D

    override suspend fun fetch(id: Int?) = flow {
        emit(Resource.Loading)
        when(val response = getApi.getItem(id)) {
            is Resource.Success -> {
                val data = mapper(response.data)
                emit(Resource.Success(data))
            }
            is Resource.Error -> emit(response)
            else -> Unit
        }
    }
}