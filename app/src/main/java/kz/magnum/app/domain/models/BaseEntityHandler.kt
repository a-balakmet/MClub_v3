package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseDao
import aab.lib.commons.data.room.BaseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class BaseEntityHandler<T : BaseEntity>(
    val dao: BaseDao<T>,
    val replacement: Boolean
) {
    fun save(remotes: List<T>, ioDispatcher: CoroutineScope) {
        if (remotes.isNotEmpty()) {
            ioDispatcher.launch {
                if (replacement) {
                    dao.deleteAll()
                }
                dao.insertList(remotes)
            }
        }
    }

    suspend fun getAll() = dao.getAll().sortedBy { it.id }
    suspend fun replace(remotes: List<T>, ioDispatcher: CoroutineScope) {
        ioDispatcher.launch {
            dao.deleteAll()
            dao.insertList(remotes)
        }
    }
}

