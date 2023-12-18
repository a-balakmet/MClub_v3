package kz.magnum.app.domain.repository.dynamics

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kz.magnum.app.data.remote.commons.PageableDto

interface PageProvider<T> {

    suspend fun fetch(query: List<QueryModel>): Flow<Resource<PageableDto<T>>>
}