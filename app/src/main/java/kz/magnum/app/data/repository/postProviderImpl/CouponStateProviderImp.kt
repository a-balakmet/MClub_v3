package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.repository.PostProviderImpl

class CouponStateProviderImp(postApi: PostApi<Map<String,Int>, Unit>): PostProviderImpl<Map<String, Int>, Unit>(postApi)