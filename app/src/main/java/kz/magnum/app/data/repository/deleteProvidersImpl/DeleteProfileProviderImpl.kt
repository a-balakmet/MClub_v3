package kz.magnum.app.data.repository.deleteProvidersImpl

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class DeleteProfileProviderImpl(
    private val api: ItemApi<Unit>,
) : ItemProvider<Unit> {

    override suspend fun fetch(id: Int?): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        when (val response = api.getItem(null)) {
            is Resource.Success -> emit(response)
            is Resource.Error -> emit(response)
            else -> Unit
        }
    }
}