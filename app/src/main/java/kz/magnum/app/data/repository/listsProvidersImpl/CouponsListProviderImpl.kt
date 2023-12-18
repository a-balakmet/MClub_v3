package kz.magnum.app.data.repository.listsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.coupons.CouponDataDto
import kz.magnum.app.data.remote.coupons.toCoupon
import kz.magnum.app.data.repository.ListProviderImpl
import kz.magnum.app.domain.models.Coupon

class CouponsListProviderImpl(getListApi: GetListApi<CouponDataDto>): ListProviderImpl<CouponDataDto, Coupon>(getListApi) {
    override val mapper = CouponDataDto::toCoupon
}