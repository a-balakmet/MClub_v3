package kz.magnum.app.data.repository.standalones

import aab.lib.commons.data.room.BaseDao
import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.Resource
import aab.lib.commons.extensions.deepEqualTo
import aab.lib.commons.utils.self
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.remote.campaigns.CampaignDto
import kz.magnum.app.data.remote.campaigns.toCampaign
import kz.magnum.app.data.remote.directory.CityDto
import kz.magnum.app.data.remote.directory.MiniGameDto
import kz.magnum.app.data.remote.directory.shops.ShopDto
import kz.magnum.app.data.remote.directory.shops.ShopPropertyDto
import kz.magnum.app.data.remote.directory.shops.ShopTypeDto
import kz.magnum.app.data.remote.directory.shops.toShop
import kz.magnum.app.data.remote.directory.shops.toShopProperty
import kz.magnum.app.data.remote.directory.shops.toShopType
import kz.magnum.app.data.remote.directory.toCity
import kz.magnum.app.data.remote.directory.toMiniGame
import kz.magnum.app.data.remote.directory.translations.TranslationsDto
import kz.magnum.app.data.remote.directory.translations.TranslationsListDto
import kz.magnum.app.data.remote.directory.translations.toTranslation
import kz.magnum.app.data.remote.discounts.DiscountsDataDto
import kz.magnum.app.data.remote.discounts.toDiscount
import kz.magnum.app.data.remote.stories.StoryDto
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Discount
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopPropertyCrossRef
import kz.magnum.app.data.room.entities.Story
import kz.magnum.app.data.room.entities.StoryScreen
import kz.magnum.app.data.room.entities.Version
import kz.magnum.app.domain.repository.VersionsSavingRepository
import kz.magnum.app.domain.repository.dynamics.ListProvider
import java.io.ByteArrayOutputStream
import java.net.URL

class VersionsSavingRepositoryImpl(
    private val campaignsApi: GetPageApi<CampaignDto>,
    private val citiesApi: GetListApi<CityDto>,
    private val clubsApi: GetListApi<Club>,
    private val discountApi: ItemApi<DiscountsDataDto>,
    private val gamesApi: GetListApi<MiniGameDto>,
    private val qrLinksApi: GetListApi<QRLink>,
    private val shoppingIconsApi: GetListApi<ShoppingIcons>,
    private val shopPropertiesApi: GetListApi<ShopPropertyDto>,
    private val shopsApi: GetListApi<ShopDto>,
    private val shopTypesApi: GetListApi<ShopTypeDto>,
    private val storiesApi: GetListApi<StoryDto>,
    private val translationsApi: ItemApi<TranslationsListDto>,
    private val versionsRepo: ListProvider<Version>,
    private val ioDispatcher: CoroutineScope,
    private val database: MagnumClubDatabase
) : VersionsSavingRepository {

    override suspend fun loadCampaigns(version: Version) {
        val resource = campaignsApi.getAllPages()
        if (resource is Resource.Success) {
            saveRemotes(
                dao = database.campaignsDao(),
                remotes = resource.data.map { it.toCampaign() }
            )
            database.versionsDao().insert(version)
        }
    }

    override suspend fun loadCities(version: Version) {
        loadAndSaveRemotes(
            api = citiesApi,
            mapper = CityDto::toCity,
            dao = database.cityDao(),
            version = version,
            checkEmptyList = true
        )
    }

    override suspend fun loadClubs(version: Version) {
        val resource = clubsApi.getList()
        if (resource is Resource.Success) {
            saveRemotes(
                dao = database.clubsDao(),
                remotes = resource.data
            )
            database.versionsDao().insert(version)
        }
    }

    override suspend fun loadDiscounts(version: Version) {
        val resource = discountApi.getItem(null)
        if (resource is Resource.Success) {
            val data = resource.data
            ioDispatcher.launch {
                val categoriesDao = database.discountsCategoriesDao()
                val typesDao = database.discountsTypesDao()
                val discountsDao = database.discountsDao()
                categoriesDao.deleteAll()
                typesDao.deleteAll()
                discountsDao.deleteAll()
                saveRemotes(dao = categoriesDao, remotes = data.categories)
                saveRemotes(dao = typesDao, remotes = data.types)
                val discounts: ArrayList<Discount> = ArrayList()
                data.categoryDiscounts.forEach { discountData ->
                    discountData.discounts.forEach { discount ->
                        val shops = discount.shopsIds?.let { database.shopDao().getShopsByIds(it) } ?: emptyList()
                        discounts.add(discount.toDiscount(discountData.category, shops))
                    }
                }
                saveRemotes(dao = discountsDao, remotes = discounts)
                database.versionsDao().insert(version)
            }
        }
    }

    override suspend fun loadGames(version: Version) {
        loadAndSaveRemotes(
            api = gamesApi,
            mapper = MiniGameDto::toMiniGame,
            dao = database.miniGamesDao(),
            version = version,
            checkEmptyList = true
        )
    }

    override suspend fun loadQRLinks(version: Version) {
        loadAndSaveRemotes(
            api = qrLinksApi,
            mapper = QRLink::self,
            dao = database.qrLinksDao(),
            version = version,
            checkEmptyList = true
        )
    }

    override suspend fun loadShoppingIcons(version: Version) {
        loadAndSaveRemotes(
            api = shoppingIconsApi,
            mapper = ShoppingIcons::self,
            dao = database.shoppingIconsDao(),
            version = version,
            checkEmptyList = true
        )
    }

    override suspend fun loadShopProperties() {
        loadAndSaveRemotes(
            api = shopPropertiesApi,
            mapper = ShopPropertyDto::toShopProperty,
            dao = database.shopPropertiesDao(),
            version = null
        )
    }

    override suspend fun loadShops(version: Version) {
        val resource = shopsApi.getList()
        if (resource is Resource.Success) {
            val remoteShops = resource.data
            database.shopDao().deleteShopPropertiesRef()
            val shops: ArrayList<Shop> = ArrayList()
            remoteShops.forEach { shopItem ->
                val city = database.cityDao().getCityById(shopItem.cityId)
                shops.add(shopItem.toShop(city))
                shopItem.properties.forEach {
                    database.shopDao().insertPropertyCrossRef(ShopPropertyCrossRef(shopItem.id, it.id))
                }
            }
            saveRemotes(dao = database.shopDao(), remotes = shops)
            database.versionsDao().insert(version)
            loadShopTypes()
            loadShopProperties()
        }
    }

    override suspend fun loadShopTypes() {
        loadAndSaveRemotes(
            api = shopTypesApi,
            mapper = ShopTypeDto::toShopType,
            dao = database.shopTypeDao(),
            version = null,
        )
    }

    override suspend fun loadStories(version: Version) {
        val resource = storiesApi.getList()
        if (resource is Resource.Success) {
            ioDispatcher.launch {
                database.storiesDao().deleteAll()
                database.storiesScreensDao().deleteAll()
                resource.data.forEach {
                    val preview = getImage(it.preview)
                    val story = Story(
                        id = it.id,
                        name = it.name,
                        storyOrder = it.sortOrder,
                        storyLink = it.preview,
                        preview = preview,
                        isViewedStory = false
                    )
                    database.storiesDao().insert(story)
                    it.storyScreens.forEach { screen ->
                        val image = getImage(screen.name)
                        val storyScreen = StoryScreen(
                            id = screen.id,
                            name = screen.name,
                            parentStoryId = it.id,
                            story = story,
                            screenOrder = screen.sortOrder,
                            actions = screen.actions,
                            image = image,
                            isViewedScreen = false
                        )
                        database.storiesScreensDao().insert(storyScreen)
                    }
                }
                database.versionsDao().insert(version)
            }
        }
    }

    override suspend fun loadTranslations(version: Version) {
        val resource = translationsApi.getItem(null)
        if (resource is Resource.Success) {
            val list = resource.data.translations.map(TranslationsDto::toTranslation)
            saveRemotes(dao = database.translationDao(), remotes = list)
            database.versionsDao().insert(version)
        }
    }

    override suspend fun loadVersion() = versionsRepo.fetch()

    override suspend fun clearDatabase() = ioDispatcher.launch {
        database.versionsDao().deleteAll()
        database.cityDao().deleteAll()
        database.shopDao().deleteAll()
        database.shopTypeDao().deleteAll()
        database.shopPropertiesDao().deleteAll()
        database.shopDao().deleteShopPropertiesRef()
        database.campaignsDao().deleteAll()
        database.clubsDao().deleteAll()
        database.discountsCategoriesDao().deleteAll()
        database.discountsTypesDao().deleteAll()
        database.discountsDao().deleteAll()
        database.storiesDao().deleteAll()
        database.storiesScreensDao().deleteAll()
        database.miniGamesDao().deleteAll()
        database.qrLinksDao().deleteAll()
        database.shoppingIconsDao().deleteAll()
    }


    private suspend inline fun <reified R, reified D : BaseEntity> loadAndSaveRemotes(
        api: GetListApi<R>,
        crossinline mapper: (R) -> D,
        dao: BaseDao<D>,
        version: Version?,
        checkEmptyList: Boolean = false
    ) {
        when (val resource = api.getList()) {
            is Resource.Success -> {
                val remotes = resource.data.map(mapper).also { list -> list.sortedBy { item -> item.id } }
                if (checkEmptyList && remotes.isNotEmpty()) {
                    saveRemotes(dao, remotes)
                    version?.let {
                        database.versionsDao().insert(version)
                    }
                } else {
                    ioDispatcher.launch {
                        dao.deleteAll()
                        dao.insertList(remotes)
                        version?.let {
                            database.versionsDao().insert(version)
                        }
                    }
                }
            }

            else -> Unit
        }
    }

    private suspend inline fun <reified D : BaseEntity> saveRemotes(dao: BaseDao<D>, remotes: List<D>) {
        ioDispatcher.launch {
            val dbList = dao.getAll()
            if (!dbList.deepEqualTo(remotes)) {
                dao.deleteAll()
                dao.insertList(remotes)
            }
        }
    }

    private suspend fun getImage(url: String): ByteArray? = withContext(Dispatchers.IO) {
        try {
            val imageUrl = URL(url)
            val connection = imageUrl.openConnection()
            val inputStream = connection.getInputStream()
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read: Int
            while (inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            return@withContext outputStream.toByteArray()
        } catch (e: Exception) {
            Log.d("ImageManager", "Error: $e")
        }
        return@withContext null
    }
}