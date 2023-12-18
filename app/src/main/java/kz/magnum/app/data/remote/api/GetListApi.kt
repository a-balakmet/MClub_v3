package kz.magnum.app.data.remote.api

import aab.lib.commons.domain.models.Resource

interface GetListApi<T> {

    suspend fun getList(): Resource<List<T>>
}