package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.coupons.CouponDataDto
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp

class GetCouponsList : GetListApiImp<CouponDataDto>() {
    override val endPoint: String = "coupon/list"
    override suspend fun backEndList(): Resource<List<CouponDataDto>> = apiCaller.getRequest(httpClient, endPoint)
}