package kz.magnum.app.domain.repository.dynamics

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface PostProvider<T, D> {

    suspend fun fetch(body: T, id: Int?): Flow<Resource<D>>
}