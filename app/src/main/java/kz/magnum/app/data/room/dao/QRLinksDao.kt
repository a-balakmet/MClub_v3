package kz.magnum.app.data.room.dao

import aab.lib.commons.data.room.BaseDao
import androidx.room.Dao
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.room.RoomNames.qrLinks

@Dao
abstract class QRLinksDao : BaseDao<QRLink>(tableName = qrLinks), QRLinksDaoInterface
