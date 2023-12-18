package kz.magnum.app.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import aab.lib.commons.data.room.BaseDao
import kz.magnum.app.data.room.RoomNames.cards
import kz.magnum.app.data.room.entities.BonusCard

@Dao
abstract class BonusCardsDao : BaseDao<BonusCard>(tableName = cards), BonusCardDaoInterface {

    @Query("SELECT * FROM $cards WHERE `order` = :orderValue")
    abstract suspend fun getPreferredCard(orderValue: Int): BonusCard
}