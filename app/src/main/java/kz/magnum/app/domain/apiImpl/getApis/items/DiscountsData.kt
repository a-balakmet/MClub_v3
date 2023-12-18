package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.extensions.toDataClass
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.discounts.DiscountsDataDto
import kz.magnum.app.domain.apiImpl.ItemApiImp
import kz.magnum.app.domain.models.profile.User

class DiscountsData(private val dataStore: DataStoreRepository) : ItemApiImp<DiscountsDataDto>() {

    override val endPoint: String = "promotion/discounts/discountFullInfo"
    override suspend fun backEndItem(id: Int?): Resource<DiscountsDataDto> {
        val userData = dataStore.readPreference(AppStoreKeys.user, "")
        val user = userData.toDataClass<User>()
        val query = listOf(QueryModel(name = "city_id", value = user?.cityId ?: 1))
        return apiCaller.getRequest(httpClient, endPoint, query = query)
    }

}