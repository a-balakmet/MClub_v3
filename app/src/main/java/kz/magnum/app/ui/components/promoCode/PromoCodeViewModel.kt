package kz.magnum.app.ui.components.promoCode

import aab.lib.commons.domain.models.LoadingState
import aab.lib.commons.domain.models.Resource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.postUseCase.PromoCodeActivateUseCase
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel

class PromoCodeViewModel(
    private val database: MagnumClubDatabase,
    useCase: ItemUseCase<PromoCodeDto>,
    private val activateUseCase: PromoCodeActivateUseCase,
) : ItemViewModel<PromoCodeDto>(useCase) {

    override val savedState: String = "promo_code"

    private var promoCodeHint = "У меня есть промокод"

    private val _exchangeState = mutableStateOf(LoadingState<Unit>())
    val exchangeState: State<LoadingState<Unit>> = _exchangeState

    var shareText = ""

    init {
        onInit()
        ioDispatcher.launch {
            val locale = dataStore.readPreference(key = AppStoreKeys.locale, "ru")
            val hint = database.translationDao().getTranslation("i_have_promo")
            promoCodeHint = when (locale) {
                "kk" -> hint?.kk ?: "Менде промо-код бар"
                else -> hint?.ru ?: "У меня есть промокод"
            }
            shareText = when (locale) {
                "kk" -> "Сәлем!\\nMagnum Club-қа қосылып артықшылықтармен пайдалан!\\nӘрбір сатып алудан бонустар ал, науқандар мен ұтыс ойындарына қатыс, тауардың бағасын оңай біл және тағы басқа артықшылықтарды қолдан. Тіркел, жарнамалық кодты қолданбаға енгіз және Magnum-нан жағымды бонус ал.\n\n"
                else -> "Привет\nВступай в Magnum Club и пользуйся привилегиями!\nПолучай бонусы с каждой покупки, участвуй в акциях и розыгрышах, легко узнавай цену товара и многое другое. Зарегистрируйся, введи этот промокод в приложении и получи приветственный бонус от Magnum\n\n"
            }
        }
    }

    fun activatePromoCode(promoCode: String) {
        viewModelScope.launch {
            val body = mapOf("promo_code" to promoCode)
            activateUseCase.invoke(body, null).collect {
                when (it) {
                    is Resource.Loading -> _exchangeState.value = LoadingState(isLoading = true)
                    is Resource.Success -> _exchangeState.value = LoadingState(result = it.data)
                    is Resource.Error -> _exchangeState.value = LoadingState(error = it)
                    else -> Unit
                }
            }
        }
    }

    fun dropErrorState() {
        _exchangeState.value = LoadingState(isLoading = false, error = null)
    }
}
