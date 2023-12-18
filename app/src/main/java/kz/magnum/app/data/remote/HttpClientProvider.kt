package kz.magnum.app.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.util.appendIfNameAbsent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import aab.lib.commons.data.dataStore.DataStoreKeys
import aab.lib.commons.data.dataStore.DataStoreRepositoryImpl
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.useCase.authentication.RefreshTokensUseCase
import java.net.HttpURLConnection

@OptIn(ExperimentalSerializationApi::class)
class HttpClientProvider(
    ioDispatcher: CoroutineScope,
    private val dataStore: DataStoreRepositoryImpl,
    private val refreshUseCase: RefreshTokensUseCase,
) {

    val httpClient = ioDispatcher.async {
        var locale = dataStore.readPreference(AppStoreKeys.locale, "")
        if (locale == "") {
            locale = "ru"
        }
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json
                val converter = KotlinxSerializationConverter(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = false
                })
                register(ContentType.Application.Json, converter)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT
                connectTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                    if (response.status.value == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        Log.v("Logger Ktor =>", "Need to refresh token")
                    }
                }
            }
            install(DefaultRequest) {
                headers.append("locale", locale)
                headers.appendIfNameAbsent(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                headers.appendIfNameAbsent(HttpHeaders.ContentType, "image/jpg")
                contentType(ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers.append("Accept", "application/json")
                headers.append("Content-type", "application/json")
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = dataStore.readPreference(key = DataStoreKeys.accessToken, defaultValue = "NNM")
                        val refToken = dataStore.readPreference(key = DataStoreKeys.refreshToken, defaultValue = "NNM")
                        BearerTokens(accessToken = token, refreshToken = refToken)
                    }
                    refreshTokens {
                        refreshUseCase.invoke().let {
                            if (it is Resource.Success) {
                                dataStore.putPreference(key = DataStoreKeys.accessToken, value = it.data.authToken)
                                dataStore.putPreference(key = DataStoreKeys.refreshToken, value = it.data.refreshToken)
                                BearerTokens(accessToken = it.data.authToken, refreshToken = it.data.refreshToken)
                            } else {
                                null
                            }
                        }
                    }
                }
            }
        }
        withContext(ioDispatcher.coroutineContext) {
            return@withContext client
        }
    }

    companion object {
        const val TIME_OUT = 20000L
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            useArrayPolymorphism = true
        }
//        const val baseUrl = "${BuildConfig.BASE_URL}v3/"
    }
}