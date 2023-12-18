package kz.magnum.app.ui.components.aboutDocs

import kz.magnum.app.domain.models.AboutDoc
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel

class AboutDocsViewModel(useCase: ListUseCase<AboutDoc>) : ListViewModel<AboutDoc>(useCase) {

    override val savedState = "messages_list"

    init {
        onInit()
    }
}