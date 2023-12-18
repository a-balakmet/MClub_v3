package kz.magnum.app.domain.repository.dynamics

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface ListProvider<T> {

    suspend fun fetch(): Flow<Resource<List<T>>>
}