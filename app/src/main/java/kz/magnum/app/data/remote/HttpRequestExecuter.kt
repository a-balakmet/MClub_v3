package kz.magnum.app.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.BuildConfig
import kz.magnum.app.data.remote.commons.ErrorDetails
import java.io.File
import java.net.UnknownHostException
import java.nio.charset.Charset
import java.text.ParseException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException

@OptIn(InternalAPI::class)
suspend inline fun <reified T, reified R> fetch(
    ktorClient: HttpClient,
    endPoint: String,
    httpMethod: HttpMethod,
    pathParam: Any? = null,
    idPathParam: Int? = null,
    requestBody: R?,
    queryParams: List<QueryModel>?,
): Resource<T> {

    val baseUrl = "${BuildConfig.BASE_URL}v3/"

    try {
        val response = if (requestBody is File) {
            ktorClient.submitFormWithBinaryData(
                url = if (idPathParam == null) "${baseUrl}$endPoint/$pathParam" else "$endPoint/$idPathParam/$pathParam",
                formData = formData {
                    append("file", requestBody.readBytes(),
                        Headers.build {
                            append(HttpHeaders.ContentType, "image/jpg")
                            append(HttpHeaders.ContentDisposition, "filename=person$pathParam")
                        }
                    )
                }
            )
        } else {
            ktorClient.request("${baseUrl}$endPoint") {
                pathParam?.let {
                    url {
                        if (idPathParam != null) {
                            appendPathSegments("$idPathParam")
                        }
                        appendPathSegments(components = arrayOf("$pathParam"), encodeSlash = true)
                    }
                } ?: run {
                    url {
                        if (idPathParam != null) {
                            appendPathSegments("$idPathParam")
                        }
                    }
                }
                contentType(ContentType.Application.Json)
                method = httpMethod
                requestBody?.let {
                    if (it !is Unit) body = Json.encodeToString(value = requestBody)
                }
                queryParams?.let { queries ->
                    url {
                        for (query in queries) {
                            if (query.value != null) {
                                parameter(query.name, query.value)
                            }
                        }
                    }
                }
            }
        }
        return if (response.status.isSuccess()) {
            Resource.Success(response.body())
        } else {
            tryParseError(response)
        }
    } catch (ex: RedirectResponseException) {
        // 3xx - responses
        return Resource.Error(
            message = ex.response.status.description,
            code = ex.response.status.toString()
        )
    } catch (ex: ClientRequestException) {
        // 4xx - responses
        return ex.toError()
    } catch (ex: ServerResponseException) {
        // 5xx - response
        return Resource.Error(
            message = ex.response.status.description,
            code = ex.response.status.toString()
        )
    } catch (ex: UnknownHostException) {
        // Unable to resolve host
        return Resource.Error(
            message = ex.localizedMessage ?: "server_unavailable",
            code = "translatable"
        )
    } catch (e: SSLHandshakeException) {
        // SSLHandshakeException
        return Resource.Error(
            message = e.localizedMessage ?: "no_network",
            code = "translatable"
        )
    } catch (ex: TimeoutException) {
        // Timeout
        return Resource.Error(
            message = ex.localizedMessage ?: "timeout",
            code = "translatable"
        )
    } catch (ex: CancellationException) {
        // Cancellation
        return Resource.Error(
            message = ex.localizedMessage ?: "canceled",
            code = "translatable"
        )
    } catch (ex: Exception) {
        // all other cases
        Log.e("Exception", " $ex")
        return Resource.Error(
            message = ex.localizedMessage ?: "no_network",
            code = "translatable"
        )
    }
}

@InternalAPI
suspend fun tryParseError(response: HttpResponse): Resource.Error {
    return try {
        val content = response.bodyAsText(Charset.defaultCharset())
        val error = Json.decodeFromString(ErrorDetails.serializer(), content)
        Resource.Error(message = error.message, code = error.code)
    } catch (ex: ParseException) {
        try {
            response.content.readUTF8Line()?.let {
                val errorBody = Json.decodeFromString(ErrorDetails.serializer(), it)
                Resource.Error(message = errorBody.message, code = errorBody.code)
            } ?: run {
                Resource.Error(message = ex.localizedMessage ?: "no_network", code = "translatable")
            }
        } catch (ex: Exception) {
            if (ex is ClientRequestException) {
                response.content.readUTF8Line()?.let {
                    val errorBody = Json.decodeFromString(ErrorDetails.serializer(), it)
                    Resource.Error(message = errorBody.message, code = errorBody.code)
                } ?: run {
                    Resource.Error(message = ex.localizedMessage ?: "no_network", code = "translatable")
                }
            } else Resource.Error(message = ex.localizedMessage ?: "no_network", code = "translatable")
        }
    }
}


@InternalAPI
suspend fun ClientRequestException.toError(): Resource.Error {
    try {
        val error = response.content.readUTF8Line()
        error?.let {
            val myException = Json.decodeFromString(ErrorDetails.serializer(), it)
            return Resource.Error(
                message = myException.message,
                code = myException.code
            )
        } ?: run {
            return Resource.Error(
                message = this.response.status.description,
                code = this.response.status.toString()
            )
        }
    } catch (e: Exception) {
        return Resource.Error(
            message = this.response.status.description,
            code = this.response.status.toString()
        )
    }
}