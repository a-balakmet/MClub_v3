package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.domain.models.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.shops.ShopDto
import kz.magnum.app.data.remote.directory.shops.toShop
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopPropertyCrossRef
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider

class ShopProviderImpl(
    private val ioDispatcher: CoroutineScope,
    private val getListApi: GetListApi<ShopDto>,
    private val database: MagnumClubDatabase,
) : DatabaseListProvider<Shop> {

    override suspend fun fetch() {
        when(val response = getListApi.getList()) {
            is Resource.Success -> {
                val remoteShops = response.data
                if (remoteShops.isNotEmpty()) {
                    database.shopDao().deleteShopPropertiesRef()
                    val shops: ArrayList<Shop> = ArrayList()
                    remoteShops.forEach { shopItem ->
                        val city = database.cityDao().getCityById(shopItem.cityId)
                        shops.add(shopItem.toShop(city))
                        shopItem.properties.forEach {
                            database.shopDao().insertPropertyCrossRef(ShopPropertyCrossRef(shopItem.id, it.id))
                        }
                    }
                    ioDispatcher.launch {
                        database.shopDao().insertList(shops)
                    }
                }
            }
            else -> Unit
        }
    }
}