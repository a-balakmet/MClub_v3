package kz.magnum.app.ui.screens.loginBlock.otpInput

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import aab.lib.commons.data.dataStore.DataStoreKeys
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.NavigationAction
import aab.lib.commons.ui.navigation.Navigator
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kz.magnum.app.BuildConfig
import kz.magnum.app.application.AppConstants.phoneRegex
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.data.remote.authentication.dto.RegDataDto
import kz.magnum.app.data.remote.authentication.dto.TokensDto
import kz.magnum.app.data.remote.profile.DeviceInfoDto
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.di.ktorClientsModule
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.repository.dynamics.ListProvider
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.postUseCase.CheckDeviceUseCase
import kz.magnum.app.domain.useCase.authentication.CheckPhoneUseCase
import kz.magnum.app.domain.useCase.authentication.RegistrationUseCase
import kz.magnum.app.ui.activity.MainActivity
import kz.magnum.app.ui.navigation.NavigationActions
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OtpInputViewModel(
    private val navigator: Navigator,
    private val checkPhoneUseCase: CheckPhoneUseCase,
//    private val deviceInfoUseCase: DeviceInfoUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val cardsProvider: ListProvider<BonusCard>,
    //private val cardsProviderUseCase: BonusCardsProviderUseCase,
    private val profileProviderUseCase: ItemUseCase<User?>,
//    private val profileProviderUseCase: ProfileProviderUseCase,
    private val deviceInfoUseCase: CheckDeviceUseCase,
    private val ioDispatcher: CoroutineScope,
    private val database: MagnumClubDatabase,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    val counter = mutableLongStateOf(60L)
    private val otp = mutableStateOf("")

    private val _exchangeState = mutableStateOf(LoadingState<TokensDto>())
    val exchangeState: State<LoadingState<TokensDto>> = _exchangeState

    init {
        countDownTime(null)
    }

    fun setOtp(sms: String) {
        otp.value = sms
    }

    fun countDownTime(phoneNo: String?) {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                counter.longValue = p0 / 1000 % 60
            }

            override fun onFinish() {}
        }
        timer.start()
        phoneNo?.let {
            viewModelScope.launch {
                val body = PhoneDto(phone = phoneNo.replace(regex = phoneRegex, replacement = ""))
                checkPhoneUseCase.invoke(body).collect {
                    when (it) {
                        is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                        is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                        else -> _exchangeState.value = LoadingState(isLoading = false)
                    }
                }
            }
        }
    }

    fun getTokens(phoneNo: String, activity: MainActivity) {
        viewModelScope.launch {
            val body = RegDataDto(code = otp.value.toInt(), phone = phoneNo.replace(regex = phoneRegex, replacement = ""))
            registrationUseCase.invoke(regData = body).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        //_exchangeState.value = LoadingState(result = it.data)
                        val job = viewModelScope.launch {
                            dataStore.putPreference(key = DataStoreKeys.accessToken, value = it.data.authToken)
                            dataStore.putPreference(key = DataStoreKeys.refreshToken, value = it.data.refreshToken)
                            unloadKoinModules(ktorClientsModule)
                            loadKoinModules(ktorClientsModule)
                        }
                        job.invokeOnCompletion {
                            activity.viewModel.runLoading()
                            firebaseRegistration()
                        }
                    }

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    private fun firebaseRegistration() {
        _exchangeState.value = LoadingState(isLoading = true)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                initRegistration(dbToken = token)
            } else {
                Log.d("mClub", "OtpInputViewModel - Firebase: ${task.exception}")
                initRegistration(dbToken = "")
            }
        }
    }

    private fun initRegistration(dbToken: String) {
        _exchangeState.value = LoadingState(isLoading = true)
        viewModelScope.launch {
            if (dbToken != "") {
                val currentDate = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                dataStore.putPreference(key = AppStoreKeys.pushTokenDate, value = currentDate)
            }
            val deviceId = dataStore.deviceId()
            val body = DeviceInfoDto(
                appBuild = BuildConfig.VERSION_CODE.toString(),
                appVersion = BuildConfig.VERSION_NAME,
                deviceId = deviceId,
                isExpired = true,
                manufacturer = Build.MANUFACTURER,
                model = Build.MODEL,
                osVersion = Build.VERSION.SDK_INT.toString(),
                platform = "android",
                pushToken = dbToken
            )
            deviceInfoUseCase.invoke(body, null).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        getCards()
                    }

                    is Resource.Error -> {
                        if ((it.code as String).contains("duplicate key")) {
                            getCards()
                        } else {
                            _exchangeState.value = LoadingState(error = it)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getCards() {
        viewModelScope.launch {
            cardsProvider.fetch().collect {
                //cardsProviderUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            _exchangeState.value = LoadingState(isLoading = false)
                            navigateNext(NavigationActions.Registration.toCardCreation())
                        } else {
                            ioDispatcher.launch {
                                database.cardsDao().insertList(it.data)
                            }.invokeOnCompletion {
                                getProfile()
                            }
                        }
                    }

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileProviderUseCase.invoke(null).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        if (it.data != null) {
                            navigateNext(NavigationActions.Commons.toPinCodeCreation(0))
                        } else {
                            navigateNext(NavigationActions.Registration.toProfileCreation())
                        }
                    }

                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    private fun navigateNext(navigationAction: NavigationAction) {
        navigator.navigate(navigationAction, 0)
    }
}