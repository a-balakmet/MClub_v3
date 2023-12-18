package kz.magnum.app.domain.useCase.pagesUseCase

import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.domain.repository.dynamics.PageProvider

class PartnersProductsUseCase(repository: PageProvider<PartnerProductDto>): PageUseCase<PartnerProductDto>(repository)