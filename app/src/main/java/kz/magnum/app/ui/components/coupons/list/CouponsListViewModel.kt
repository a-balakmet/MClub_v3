package kz.magnum.app.ui.components.coupons.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.models.ListUIEvent
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel

class CouponsListViewModel(useCase: ListUseCase<Coupon>) : ListViewModel<Coupon>(useCase) {

    override val savedState = "coupons_list"

    init {
        onInit()
        viewModelScope.launch {
            dataStore.getPreference(AppStoreKeys.refresher, false).collect {
                if (it) {
                    onEvent(event = ListUIEvent.Refresh(null))
                }
            }
        }
    }

}