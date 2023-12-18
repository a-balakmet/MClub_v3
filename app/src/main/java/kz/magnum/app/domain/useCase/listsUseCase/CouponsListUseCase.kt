package kz.magnum.app.domain.useCase.listsUseCase

import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.repository.dynamics.ListProvider

class CouponsListUseCase(repository: ListProvider<Coupon>) : ListUseCase<Coupon>(repository)