package kz.magnum.app.data.repository.databaseListsProvidersImpl

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.extensions.deepEqualTo
import kotlinx.coroutines.CoroutineScope
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.di.DINames
import kz.magnum.app.domain.models.BaseEntityHandler
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

abstract class DatabaseListProviderImpl<T, D : BaseEntity>(
    private val getListApi: GetListApi<T>
) : DatabaseListProvider<D> {

    val ioDispatcher: CoroutineScope by inject(CoroutineScope::class.java, named(DINames.ioDispatcher))
    val database: MagnumClubDatabase by inject(MagnumClubDatabase::class.java)
    abstract val dbHandler: BaseEntityHandler<D>
    abstract val mapper: (T) -> D
    abstract val fullReplacement: Boolean

    override suspend fun fetch() {
        when (val response = getListApi.getList()) {
            is Resource.Success -> {
                val beData = response.data.map { it.let(mapper) }.sortedBy { it.id }
                if (fullReplacement) {
                    dbHandler.replace(beData, ioDispatcher)
                } else {
                    val dbData = dbHandler.getAll()
                    if (!beData.deepEqualTo(dbData)) {
                        dbHandler.save(beData, ioDispatcher)
                    }
                }
            }

            else -> Unit
        }
    }
}