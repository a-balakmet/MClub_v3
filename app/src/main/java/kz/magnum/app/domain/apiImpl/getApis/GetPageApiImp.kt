package kz.magnum.app.domain.apiImpl.getApis

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Deferred
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.remote.commons.ApiCaller
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.di.DINames
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class GetPageApiImp<T> : GetPageApi<T> {

    abstract val endPoint: String

    val httpClient: Deferred<HttpClient> by inject(Deferred::class.java, named(DINames.commonClient))
    val apiCaller: ApiCaller by inject(ApiCaller::class.java)

     override suspend fun getPage(query: List<QueryModel>): Resource<PageableDto<T>> {
         return backEndPage(query)
     }

    override suspend fun getAllPages(): Resource<List<T>> {
        return backEndPages()
    }

    abstract suspend fun backEndPage(query: List<QueryModel>): Resource<PageableDto<T>>

    abstract suspend fun backEndPages(): Resource<List<T>>
 }