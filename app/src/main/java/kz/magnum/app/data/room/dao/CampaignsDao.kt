package kz.magnum.app.data.room.dao

import androidx.room.Dao
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.campaigns
import kz.magnum.app.data.room.entities.Campaign

@Dao
abstract class CampaignsDao : BaseDao<Campaign>(tableName = campaigns), CampaignDaoInterface