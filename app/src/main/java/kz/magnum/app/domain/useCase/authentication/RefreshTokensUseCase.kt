package kz.magnum.app.domain.useCase.authentication

import kz.magnum.app.data.remote.authentication.AuthenticationApi

class RefreshTokensUseCase(private val api: AuthenticationApi) {

    suspend operator fun invoke() = api.refreshToken()
}