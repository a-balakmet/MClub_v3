package kz.magnum.app.domain.apiImpl

import aab.lib.commons.domain.models.Resource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Deferred
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.commons.ApiCaller
import kz.magnum.app.di.DINames
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class ItemApiImp<T> : ItemApi<T> {

    abstract val endPoint: String

    val httpClient: Deferred<HttpClient> by inject(Deferred::class.java, named(DINames.commonClient))
    val apiCaller: ApiCaller by inject(ApiCaller::class.java)

    override suspend fun getItem(id: Int?): Resource<T> {
        return backEndItem(id = id)
    }

    abstract suspend fun backEndItem(id: Int? = null): Resource<T>
}