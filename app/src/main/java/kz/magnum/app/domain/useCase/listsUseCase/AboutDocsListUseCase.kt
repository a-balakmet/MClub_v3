package kz.magnum.app.domain.useCase.listsUseCase

import kz.magnum.app.domain.models.AboutDoc
import kz.magnum.app.domain.repository.dynamics.ListProvider

class AboutDocsListUseCase(repository: ListProvider<AboutDoc>) : ListUseCase<AboutDoc>(repository)