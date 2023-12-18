package kz.magnum.app.domain.useCase.pagesUseCase

import kz.magnum.app.domain.models.transactions.Transaction
import kz.magnum.app.domain.repository.dynamics.PageProvider

class TransactionsUseCase(repository: PageProvider<Transaction>): PageUseCase<Transaction>(repository)