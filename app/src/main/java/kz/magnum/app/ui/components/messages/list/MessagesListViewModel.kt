package kz.magnum.app.ui.components.messages.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.domain.models.Message
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel

class MessagesListViewModel(useCase: ListUseCase<Message>) : ListViewModel<Message>(useCase) {

    override val savedState = "messages_list"

    var locale = "ru"

    init {
        viewModelScope.launch {
            locale = dataStore.readPreference(AppStoreKeys.locale, "ru")
        }
        onInit()
    }
}