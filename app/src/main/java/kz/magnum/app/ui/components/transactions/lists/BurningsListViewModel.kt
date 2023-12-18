package kz.magnum.app.ui.components.transactions.lists

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.models.Burning
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel

class BurningsListViewModel(useCase: ListUseCase<Burning>) : ListViewModel<Burning>(useCase) {

    override val savedState = "burnings_list"

    var locale = "ru"

    init {
        viewModelScope.launch {
            locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
        }
        onInit()
    }
}