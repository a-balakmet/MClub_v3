package kz.magnum.app.data.remote.api

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.commons.PageableDto

interface GetPageApi<T> {

    suspend fun getPage(query: List<QueryModel>): Resource<PageableDto<T>>
    suspend fun getAllPages(): Resource<List<T>>
}