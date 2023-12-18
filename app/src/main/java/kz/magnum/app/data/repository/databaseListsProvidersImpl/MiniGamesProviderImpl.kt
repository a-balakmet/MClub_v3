package kz.magnum.app.data.repository.databaseListsProvidersImpl

import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.directory.MiniGameDto
import kz.magnum.app.data.remote.directory.toMiniGame
import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.domain.models.BaseEntityHandler

class MiniGamesProviderImpl(getListApi: GetListApi<MiniGameDto>) : DatabaseListProviderImpl<MiniGameDto, MiniGame>(getListApi) {

    override val dbHandler: BaseEntityHandler<MiniGame> = BaseEntityHandler(
        dao = database.miniGamesDao(),
        replacement = false
    )

    override val mapper = MiniGameDto::toMiniGame
    override val fullReplacement: Boolean = true
}