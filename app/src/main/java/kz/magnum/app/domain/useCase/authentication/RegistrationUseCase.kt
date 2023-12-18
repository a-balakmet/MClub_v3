package kz.magnum.app.domain.useCase.authentication

import kz.magnum.app.data.remote.authentication.AuthenticationApi
import kz.magnum.app.data.remote.authentication.dto.RegDataDto

class RegistrationUseCase(private val authApi: AuthenticationApi) {

    suspend operator fun invoke(regData: RegDataDto) = authApi.registration(regData)
}