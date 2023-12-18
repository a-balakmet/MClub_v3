package kz.magnum.app.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.DiscountCategory
import kz.magnum.app.data.commonModels.DiscountType
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.room.dao.BonusCardsDao
import kz.magnum.app.data.room.dao.CampaignsDao
import kz.magnum.app.data.room.dao.CityDao
import kz.magnum.app.data.room.dao.ClubsDao
import kz.magnum.app.data.room.dao.DiscountsCategoriesDao
import kz.magnum.app.data.room.dao.DiscountsDao
import kz.magnum.app.data.room.dao.DiscountsTypesDao
import kz.magnum.app.data.room.dao.MiniGamesDao
import kz.magnum.app.data.room.dao.QRLinksDao
import kz.magnum.app.data.room.dao.ShopPropertiesDao
import kz.magnum.app.data.room.dao.ShopTypeDao
import kz.magnum.app.data.room.dao.ShoppingIconsDao
import kz.magnum.app.data.room.dao.ShopsDao
import kz.magnum.app.data.room.dao.StoriesDao
import kz.magnum.app.data.room.dao.StoriesScreensDao
import kz.magnum.app.data.room.dao.StoriesViewedDao
import kz.magnum.app.data.room.dao.TranslationDao
import kz.magnum.app.data.room.dao.VersionsDao
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.data.room.entities.City
import kz.magnum.app.data.room.entities.Discount
import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopProperty
import kz.magnum.app.data.room.entities.ShopPropertyCrossRef
import kz.magnum.app.data.room.entities.ShopType
import kz.magnum.app.data.room.entities.StoriesViewed
import kz.magnum.app.data.room.entities.Story
import kz.magnum.app.data.room.entities.StoryScreen
import kz.magnum.app.data.room.entities.Translation
import kz.magnum.app.data.room.entities.Version

@Database(
    entities = [
        BonusCard::class,
        Version::class,
        City::class,
        Shop::class,
        ShopType::class,
        ShopProperty::class,
        ShopPropertyCrossRef::class,
        Translation::class,
        Campaign::class,
        Club::class,
        Discount::class,
        DiscountType::class,
        DiscountCategory::class,
        ShoppingIcons::class,
        Story::class,
        StoryScreen::class,
        StoriesViewed::class,
        MiniGame::class,
        QRLink::class
    ],
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3),
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(RoomConverter::class)
abstract class MagnumClubDatabase : RoomDatabase() {
    abstract fun cardsDao(): BonusCardsDao
    abstract fun versionsDao(): VersionsDao
    abstract fun cityDao(): CityDao
    abstract fun shopDao(): ShopsDao
    abstract fun shopTypeDao(): ShopTypeDao
    abstract fun shopPropertiesDao(): ShopPropertiesDao
    abstract fun translationDao(): TranslationDao
    abstract fun campaignsDao(): CampaignsDao
    abstract fun clubsDao(): ClubsDao
    abstract fun discountsCategoriesDao(): DiscountsCategoriesDao
    abstract fun discountsTypesDao(): DiscountsTypesDao
    abstract fun discountsDao(): DiscountsDao
    abstract fun shoppingIconsDao(): ShoppingIconsDao
    abstract fun storiesDao(): StoriesDao
    abstract fun storiesScreensDao(): StoriesScreensDao
    abstract fun storiesViewedDao(): StoriesViewedDao
    abstract fun miniGamesDao(): MiniGamesDao
    abstract fun qrLinksDao(): QRLinksDao
}