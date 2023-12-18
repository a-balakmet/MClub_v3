package kz.magnum.app.data.remote.api

import aab.lib.commons.domain.models.Resource

interface PostApi<T,R> {

    suspend fun postItem(body: T, id: Int?): Resource<R>
}