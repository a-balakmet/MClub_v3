package kz.magnum.app.ui.screens.loginBlock.phoneInput

import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.Navigator
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.application.AppConstants.phoneRegex
import kz.magnum.app.application.extenions.formatPhone
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.domain.useCase.authentication.CheckPhoneUseCase
import kz.magnum.app.ui.navigation.NavigationActions

class PhoneInputViewModel(
    private val useCase: CheckPhoneUseCase,
    private val navigator: Navigator,
) : ViewModel() {

    private val _checkPhoneState = mutableStateOf(LoadingState<Unit>())
    val checkPhoneState: State<LoadingState<Unit>> = _checkPhoneState

    var phoneNumber by mutableStateOf(
        TextFieldValue(
            text = ""
        )
    )

    fun formatNumber(number: String) {
        val phone = number.replace(regex = phoneRegex, replacement = "").formatPhone()
        phoneNumber = TextFieldValue(
            text = phone,
            selection = TextRange(phone.length)
        )
    }

    fun deleteLast() {
        val phone = phoneNumber.text
        if (phone.length == 5) {
            phoneNumber = TextFieldValue(
                text = phone.replace(")", ""),
                selection = TextRange(phone.length)
            )
        }
    }

    fun checkPhone() {
        viewModelScope.launch {
            val body = PhoneDto(phone = phoneNumber.text.replace(regex = phoneRegex, replacement = ""))
            useCase.invoke(body).collect {
                when (it) {
                    is Resource.Loading -> _checkPhoneState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        _checkPhoneState.value = LoadingState(isLoading = false)
                        navigator.navigate(
                            navigationAction = NavigationActions.Registration.toOtpInput(phone = phoneNumber.text),
                            bottomIndex = null
                        )
                    }

                    is Resource.Error -> _checkPhoneState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }
}