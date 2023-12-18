package kz.magnum.app.domain.useCase.listsUseCase

import kz.magnum.app.domain.models.Message
import kz.magnum.app.domain.repository.dynamics.ListProvider

class MessagesListUseCase(repository: ListProvider<Message>) : ListUseCase<Message>(repository)