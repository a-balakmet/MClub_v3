package kz.magnum.app.domain.repository.dynamics

import aab.lib.commons.data.room.BaseEntity

interface DatabaseListProvider<T: BaseEntity> {

    suspend fun fetch()
}