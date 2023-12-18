package kz.magnum.app.data.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import aab.lib.commons.extensions.toDataClass
import aab.lib.commons.extensions.toObjectsList
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.DiscountTerms
import kz.magnum.app.data.commonModels.StoryAction
import kz.magnum.app.data.room.relations.ShopWithProperties
import java.time.LocalDateTime


class RoomConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromList(list: List<String>) = Json.encodeToString(list)

    @TypeConverter
    fun fromClub(club: Club?): String? {
        if (club == null) {
            return null
        }
        return Json.encodeToString(club)
    }

    @TypeConverter
    fun toClub(value: String?): Club? {
        return value?.toDataClass<Club>()
    }
    @TypeConverter
    fun fromTerms(terms: DiscountTerms): String {
        return Json.encodeToString(terms)
    }

    @TypeConverter
    fun toTerms(value: String): DiscountTerms {
        return value.toDataClass<DiscountTerms>()!!
    }

    @TypeConverter
    fun fromShopsList(shops: List<ShopWithProperties>): String {
        return Json.encodeToString(shops)
    }

    @TypeConverter
    fun toShopsList(value: String): List<ShopWithProperties> {
        return value.toObjectsList()
    }

    @TypeConverter
    fun fromStoryActionsList(actions: List<StoryAction>?): String? {
        if (actions == null) {
            return null
        }
        return Json.encodeToString(actions)
    }

    @TypeConverter
    fun toStoryActionsList(value: String?): List<StoryAction>? {
        return value?.toObjectsList()
    }

}