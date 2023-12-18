package kz.magnum.app.domain.apiImpl

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import aab.lib.commons.data.dataStore.DataStoreKeys
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.authentication.AuthenticationApi
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.data.remote.authentication.dto.RefreshTokenDto
import kz.magnum.app.data.remote.authentication.dto.RegDataDto
import kz.magnum.app.data.remote.authentication.dto.TokensDto
import kz.magnum.app.data.remote.fetch

class AuthenticationApiImpl(private val httpClient: HttpClient, private val dataStore: DataStoreRepository) : AuthenticationApi {

    companion object {
        const val checkPhone = "auth/checkPhone"
        const val registration = "auth/registration"
        const val refresh = "auth/refresh"
    }

    override suspend fun checkPhone(body: PhoneDto): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        val response = fetch<Unit, PhoneDto>(
            ktorClient = httpClient,
            endPoint = checkPhone,
            httpMethod = HttpMethod.Post,
            requestBody = body,
            queryParams = null,
        )
        emit(response)
    }

    override suspend fun registration(body: RegDataDto): Flow<Resource<TokensDto>> = flow {
        emit(Resource.Loading)
        val response = fetch<TokensDto, RegDataDto>(
            ktorClient = httpClient,
            endPoint = registration,
            httpMethod = HttpMethod.Post,
            requestBody = body,
            queryParams = null,
        )
        emit(response)
    }

    override suspend fun refreshToken(): Resource<TokensDto> {
        val refreshToken = dataStore.readPreference(DataStoreKeys.refreshToken, "NNM")
        val body = RefreshTokenDto(refreshToken)
        return fetch(
            ktorClient = httpClient,
            endPoint = refresh,
            httpMethod = HttpMethod.Post,
            requestBody = body,
            queryParams = null,
        )
    }
}