package kz.magnum.app.domain.apiImpl.getApis.lists

import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.accounting.BurningDto
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.apiImpl.getApis.GetListApiImp
import org.koin.java.KoinJavaComponent.inject

class GetBurningsList : GetListApiImp<BurningDto>() {
    override val endPoint: String = "accounting/cards/"
    override suspend fun backEndList(): Resource<List<BurningDto>> {
        val database: MagnumClubDatabase by inject(MagnumClubDatabase::class.java)
        val cards = database.cardsDao().getAll()
        cards.sortedBy { it.type }
        val suffix = "${cards.first().id}/burnSchedule"
        return apiCaller.getRequest(httpClient, "$endPoint$suffix", listOf(QueryModel("size", 20)))
    }
}