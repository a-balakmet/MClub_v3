package kz.magnum.app.ui.components.productsDialog

import aab.lib.commons.domain.models.QueryModel
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.domain.useCase.pagesUseCase.PageUseCase
import kz.magnum.app.ui.builders.baseViewModels.PagedListViewModel

class PartnerProductViewModel(useCase: PageUseCase<PartnerProductDto>) : PagedListViewModel<PartnerProductDto>(useCase) {

    override var queriesList: List<QueryModel>? = null

    init {
        onInit()
    }

    override fun onRefresh() {}

}