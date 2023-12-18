package kz.magnum.app.domain.apiImpl.getApis

import aab.lib.commons.domain.models.Resource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Deferred
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.commons.ApiCaller
import kz.magnum.app.di.DINames
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class GetListApiImp<T> : GetListApi<T> {

    abstract val endPoint: String

    val httpClient: Deferred<HttpClient> by inject(Deferred::class.java, named(DINames.commonClient))
    val apiCaller: ApiCaller by inject(ApiCaller::class.java)

    override suspend fun getList(): Resource<List<T>> {
        return backEndList()
    }

    abstract suspend fun backEndList(): Resource<List<T>>
}