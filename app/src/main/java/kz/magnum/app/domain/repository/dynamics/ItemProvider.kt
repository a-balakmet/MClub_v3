package kz.magnum.app.domain.repository.dynamics

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow


interface ItemProvider<T> {

    suspend fun fetch(id: Int?): Flow<Resource<T>>
}