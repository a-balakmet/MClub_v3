package kz.magnum.app.di

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kz.magnum.app.data.remote.HttpClientProvider
import kz.magnum.app.di.DINames.authClient
import kz.magnum.app.di.DINames.commonClient
import kz.magnum.app.di.DINames.ioDispatcher
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.net.HttpURLConnection

@OptIn(ExperimentalSerializationApi::class)
val ktorClientsModule: Module = module {

    single(named(commonClient)) {
        val provider = HttpClientProvider(
            ioDispatcher = get(named(ioDispatcher)),
            dataStore = get(),
            refreshUseCase = get()
        )
        provider.httpClient
    }

    single(named(authClient)) {
        HttpClient(Android) {
            install(ContentNegotiation) {
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
                val converter = KotlinxSerializationConverter(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = false
                })
                register(ContentType.Application.Json, converter)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = HttpClientProvider.TIME_OUT
                connectTimeoutMillis = HttpClientProvider.TIME_OUT
                socketTimeoutMillis = HttpClientProvider.TIME_OUT
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
                headers.appendIfNameAbsent(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                contentType(ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                headers.append("Accept", "application/json")
                headers.append("Content-type", "application/json")
            }
        }
    }
}