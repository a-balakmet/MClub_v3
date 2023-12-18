package kz.magnum.app.domain.apiImpl.postApis

import aab.lib.commons.domain.models.Resource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Deferred
import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.commons.ApiCaller
import kz.magnum.app.di.DINames
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class PostApiImpl<T,D>: PostApi<T, D> {

    abstract val endPoint: String

    val httpClient: Deferred<HttpClient> by inject(Deferred::class.java, named(DINames.commonClient))
    val apiCaller: ApiCaller by inject(ApiCaller::class.java)

    override suspend fun postItem(body: T, id: Int?): Resource<D> {
        return fetch(body, id)
    }

    abstract suspend fun fetch(body: T, id: Int?): Resource<D>
}