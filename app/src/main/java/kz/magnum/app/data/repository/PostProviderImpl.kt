package kz.magnum.app.data.repository

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.domain.repository.dynamics.PostProvider

abstract class PostProviderImpl<T, D>(
    private val postApi: PostApi<T, D>
) : PostProvider<T, D> {


    override suspend fun fetch(body: T, id: Int?): Flow<Resource<D>> = flow {
        emit(Resource.Loading)
        when (val response = postApi.postItem(body, id)) {
            is Resource.Success -> {
                emit(response)
            }
            is Resource.Error -> emit(response)
            else -> Unit
        }
    }
}