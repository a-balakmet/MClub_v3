package kz.magnum.app.data.remote.commons

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.Deferred
import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.fetch

open class ApiCaller {
    suspend inline fun <reified T> getRequest(httpClient: Deferred<HttpClient>, endPoint: String, query: List<QueryModel>? = null): Resource<T> {
        val client = httpClient.await()
        return fetch<T, Unit>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Get,
            requestBody = null,
            queryParams = query,
        )
    }

    suspend inline fun <reified T> getPages(httpClient: Deferred<HttpClient>, endPoint: String): Resource<List<T>> {
        val list: ArrayList<T> = ArrayList()
        var totalPages = 0
        var currentPage = -1
        while (currentPage < totalPages) {
            currentPage++
            val query = listOf(
                QueryModel(name = pageNo, value = currentPage),
                QueryModel(name = pageSize, value = 20)
            )
            val response = getRequest<PageableDto<T>>(httpClient, endPoint, query)
            if (response is Resource.Success) {
                totalPages = response.data.totalPages
                list.addAll(response.data.content)
            }
        }
        return Resource.Success(data = list)
    }

    suspend inline fun <reified T> getRequestWithItemId(httpClient: Deferred<HttpClient>, endPoint: String, id: Int?): Resource<T> {
        val client = httpClient.await()
        return fetch<T, Unit>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Get,
            pathParam = id,
            requestBody = null,
            queryParams = null,
        )
    }

    suspend inline fun <reified T> getRequestWithSegmentedItemId(httpClient: Deferred<HttpClient>, endPoint: String, pathParam: String, query: List<QueryModel>? = null): Resource<T> {
        val client = httpClient.await()
        return fetch<T, Unit>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Get,
            pathParam = pathParam,
            requestBody = null,
            queryParams = null,
        )
    }

    suspend inline fun <reified T, reified R> postRequest(
        httpClient: Deferred<HttpClient>,
        endPoint: String,
        pathParam: Any? = null,
        idPathParam: Int? = null,
        body: R
    ): Resource<T> {
        val client = httpClient.await()
        return fetch<T, R>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Post,
            pathParam = pathParam,
            idPathParam = idPathParam,
            requestBody = body,
            queryParams = null,
        )
    }

    suspend inline fun <reified T, reified R> patchRequest(
        httpClient: Deferred<HttpClient>,
        endPoint: String,
        body: R
    ): Resource<T> {
        val client = httpClient.await()
        return fetch<T, R>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Patch,
            requestBody = body,
            queryParams = null,
        )
    }

    suspend inline fun <reified T> deleteRequest(
        httpClient: Deferred<HttpClient>,
        endPoint: String, pathParam: Any? = null,
        idPathParam: Int? = null
    ): Resource<T> {
        val client = httpClient.await()
        return fetch<T, Unit>(
            ktorClient = client,
            endPoint = endPoint,
            httpMethod = HttpMethod.Delete,
            pathParam = pathParam,
            idPathParam = idPathParam,
            requestBody = null,
            queryParams = null,
        )
    }

    companion object {
        const val pageNo = "page"
        const val pageSize = "size"
    }
}