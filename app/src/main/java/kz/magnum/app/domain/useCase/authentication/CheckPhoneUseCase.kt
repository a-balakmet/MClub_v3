package kz.magnum.app.domain.useCase.authentication

import kz.magnum.app.data.remote.authentication.AuthenticationApi
import kz.magnum.app.data.remote.authentication.dto.PhoneDto

class CheckPhoneUseCase(private val api: AuthenticationApi) {

    suspend operator fun invoke(phone: PhoneDto) = api.checkPhone(phone)
}