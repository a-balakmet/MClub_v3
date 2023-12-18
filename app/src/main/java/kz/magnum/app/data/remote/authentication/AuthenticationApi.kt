package kz.magnum.app.data.remote.authentication

import kotlinx.coroutines.flow.Flow
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.data.remote.authentication.dto.RegDataDto
import kz.magnum.app.data.remote.authentication.dto.TokensDto

interface AuthenticationApi {

    suspend fun checkPhone(body: PhoneDto) : Flow<Resource<Unit>>
    suspend fun registration(body: RegDataDto): Flow<Resource<TokensDto>>
    suspend fun refreshToken(): Resource<TokensDto>

}