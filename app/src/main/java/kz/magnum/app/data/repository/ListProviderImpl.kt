package kz.magnum.app.data.repository

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.flow
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.domain.repository.dynamics.ListProvider

abstract class ListProviderImpl<T, D>(
    private val getListApi: GetListApi<T>,
): ListProvider<D> {

    abstract val mapper: (T) -> D

    override suspend fun fetch() = flow {
        emit(Resource.Loading)
        when(val response = getListApi.getList()) {
            is Resource.Success -> {
                val data = response.data.map { it.let ( mapper ) }
                emit(Resource.Success(data))
            }
            is Resource.Error -> emit(response)
            else -> Unit
        }
    }
}