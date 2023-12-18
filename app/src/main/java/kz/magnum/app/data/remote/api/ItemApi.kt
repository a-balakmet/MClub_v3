package kz.magnum.app.data.remote.api

import aab.lib.commons.domain.models.Resource

interface ItemApi<T> {

    suspend fun getItem(id: Int?): Resource<T>
}