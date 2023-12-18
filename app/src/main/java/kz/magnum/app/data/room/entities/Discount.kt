package kz.magnum.app.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import aab.lib.commons.data.room.BaseEntity
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.commonModels.DiscountTerms
import kz.magnum.app.data.room.RoomConverter
import kz.magnum.app.data.room.RoomNames
import kz.magnum.app.data.room.RoomNames.discountId
import kz.magnum.app.data.room.RoomNames.discountImageLink
import kz.magnum.app.data.room.RoomNames.discountName
import kz.magnum.app.data.room.RoomNames.discounts
import kz.magnum.app.data.room.relations.ShopWithProperties
import java.time.LocalDateTime

@Entity(
    tableName = discounts,
    indices = [Index(RoomNames.categoryId)],
    foreignKeys = [
        ForeignKey(
            entity = DiscountCategory::class,
            childColumns = [RoomNames.categoryId],
            parentColumns = [RoomNames.categoryId],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Discount(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = discountId) override val id: Int,
    @ColumnInfo(name = discountName) override val name: String,
    @TypeConverters(RoomConverter::class) val dateStart: LocalDateTime,
    @TypeConverters(RoomConverter::class) val dateEnd: LocalDateTime,
    val kzName: String?,
    @TypeConverters(RoomConverter::class) val shops: List<ShopWithProperties>,
    @ColumnInfo(name = "category_$discountId") val categoryId: Int,
    @Embedded val category: DiscountCategory,
    val typeId: Int,
    @TypeConverters(RoomConverter::class) val club: Club?,
    @TypeConverters(RoomConverter::class) val discountTerms: DiscountTerms,
    @ColumnInfo(name = discountImageLink) val imageUrl: String?,
    val isBestPrice: Boolean,
    val wareReference: String
) : BaseEntity()
