package kz.magnum.app.data.repository.databaseListsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.CityDto
import kz.magnum.app.data.remote.directory.toCity
import kz.magnum.app.data.room.entities.City
import kz.magnum.app.domain.models.BaseEntityHandler

class CitiesProviderImpl(
    getListApi: GetListApi<CityDto>
) : DatabaseListProviderImpl<CityDto, City>(getListApi) {


    override val dbHandler: BaseEntityHandler<City> = BaseEntityHandler(
        dao = database.cityDao(),
        replacement = false
    )

    override val mapper = CityDto::toCity
    override val fullReplacement: Boolean = true
}