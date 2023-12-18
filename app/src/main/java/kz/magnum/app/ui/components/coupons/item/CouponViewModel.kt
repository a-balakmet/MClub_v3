package kz.magnum.app.ui.components.coupons.item

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.useCase.postUseCase.CouponStateUseCase

class CouponViewModel(
    private val ioDispatcher: CoroutineScope,
    private val couponStateUseCase: CouponStateUseCase,
    private val dataStore: DataStoreRepository,
): ViewModel() {

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    fun changeCouponState(coupon: Coupon) {
        viewModelScope.launch {
            dataStore.putPreference(key = AppStoreKeys.refresher, false)
            couponStateUseCase.invoke(mapOf("coupon_client_id" to coupon.id), coupon.stateInt).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> {
                        dataStore.putPreference(key = AppStoreKeys.refresher, true)
                        _exchangeState.value = LoadingState(result = it.data)
                    }
                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    fun saveQueryType(couponId: Int) = ioDispatcher.async{
        viewModelScope.launch {
            dataStore.putPreference(AppStoreKeys.savedId, couponId)
            dataStore.putPreference(AppStoreKeys.savedQuery, "coupon")
        }
    }

    fun dropState() {
        _exchangeState.value = LoadingState(isLoading = false)
    }

    override fun onCleared() {
        viewModelScope.launch{
            dataStore.removePreference(AppStoreKeys.savedQuery)
        }
        super.onCleared()
    }
}