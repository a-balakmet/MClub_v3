package kz.magnum.app.domain.useCase.postUseCase

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.magnum.app.domain.repository.dynamics.PostProvider

abstract class PostUseCase<T, R, D>(private val repository: PostProvider<T, R>) {


    suspend operator fun invoke(body: T, id: Int?, mapper: ((R) -> D)): Flow<Resource<D>> = flow {
        repository.fetch(body, id).collect {
            when (it) {
                is Resource.Loading -> emit(it)
                is Resource.Success -> {
                    val data = mapper(it.data)
                    emit(Resource.Success(data))
                }

                is Resource.Error -> emit(it)
                else -> Unit
            }
        }
    }

    suspend operator fun invoke(body: T, id: Int?): Flow<Resource<R>> = flow {
        repository.fetch(body, id).collect {
            when (it) {
                is Resource.Loading -> emit(it)
                is Resource.Success -> emit(it)
                is Resource.Error -> emit(it)
                else -> Unit
            }
        }
    }
}