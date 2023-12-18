package kz.magnum.app.di

import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.authentication.dto.PhoneDto
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.data.remote.discounts.DiscountsDataDto
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.data.remote.profile.DeviceInfoDto
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto
import kz.magnum.app.data.remote.promotions.StickerExchangeDto
import kz.magnum.app.data.repository.databaseListsProvidersImpl.BonusCardsProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.CampaignsProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.CitiesProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.ClubsProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.MiniGamesProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.QRLinksProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.ShopPropertiesProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.ShopProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.ShopTypesProviderImpl
import kz.magnum.app.data.repository.databaseListsProvidersImpl.ShoppingIconsProviderImpl
import kz.magnum.app.data.repository.deleteProvidersImpl.DeleteProfileProviderImpl
import kz.magnum.app.data.repository.deleteProvidersImpl.ExitClubProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.CampaignProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.ClubProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.DiscountsDataProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.MonthProgressProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.ProfileProviderRepositoryImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.PromoCodeProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.SurveysCountProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.TransactionProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.TranslationsProviderImpl
import kz.magnum.app.data.repository.itemsProvidersImpl.UnreadMessagesProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.AboutDocsListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.BonusCardsListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.BurningsListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.CouponsListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.MainButtonsListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.MessagesListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.QRLinksListProviderImpl
import kz.magnum.app.data.repository.listsProvidersImpl.VersionsListProviderImpl
import kz.magnum.app.data.repository.pageProviderImpl.PartnersProductsProviderImpl
import kz.magnum.app.data.repository.pageProviderImpl.TransactionsProviderImpl
import kz.magnum.app.data.repository.postProviderImpl.BonusCardBindProviderImp
import kz.magnum.app.data.repository.postProviderImpl.CheckDeviceProviderImp
import kz.magnum.app.data.repository.postProviderImpl.CheckPhoneProviderImp
import kz.magnum.app.data.repository.postProviderImpl.CouponStateProviderImp
import kz.magnum.app.data.repository.postProviderImpl.EnterClubProviderImp
import kz.magnum.app.data.repository.postProviderImpl.IINProviderImp
import kz.magnum.app.data.repository.postProviderImpl.ProfileEditProviderImp
import kz.magnum.app.data.repository.postProviderImpl.PromoCodeActivateProviderImp
import kz.magnum.app.data.repository.postProviderImpl.StickerExchangeProviderImp
import kz.magnum.app.data.repository.standalones.TranslationRepositoryImpl
import kz.magnum.app.data.repository.standalones.VersionsSavingRepositoryImpl
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.data.room.entities.City
import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.data.room.entities.Shop
import kz.magnum.app.data.room.entities.ShopProperty
import kz.magnum.app.data.room.entities.ShopType
import kz.magnum.app.data.room.entities.Translation
import kz.magnum.app.data.room.entities.Version
import kz.magnum.app.domain.models.AboutDoc
import kz.magnum.app.domain.models.Burning
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.models.Message
import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.models.transactions.Transaction
import kz.magnum.app.domain.models.transactions.TransactionContent
import kz.magnum.app.domain.repository.TranslationRepository
import kz.magnum.app.domain.repository.VersionsSavingRepository
import kz.magnum.app.domain.repository.dynamics.DatabaseListProvider
import kz.magnum.app.domain.repository.dynamics.ItemProvider
import kz.magnum.app.domain.repository.dynamics.ListProvider
import kz.magnum.app.domain.repository.dynamics.PageProvider
import kz.magnum.app.domain.repository.dynamics.PostProvider
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule: Module = module {

    /** Standalone repositories */
    single<VersionsSavingRepository> {
        VersionsSavingRepositoryImpl(
            campaignsApi = get(named(DINames.campaignsApi)),
            citiesApi = get(named(DINames.citiesApi)),
            clubsApi = get(named(DINames.clubsApi)),
            discountApi = get(named(DINames.discountsDataApi)),
            gamesApi = get(named(DINames.miniGamesApi)),
            qrLinksApi = get(named(DINames.qrLinksApi)),
            shoppingIconsApi = get(named(DINames.shoppingIconsApi)),
            shopPropertiesApi = get(named(DINames.shopPropertiesApi)),
            shopsApi = get(named(DINames.shopsApi)),
            shopTypesApi = get(named(DINames.shopTypesApi)),
            storiesApi = get(named(DINames.storiesApi)),
            translationsApi = get(named(DINames.translationsApi)),
            versionsRepo = get(named(DINames.versionsRepo)),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            database = get()
        )
    }

    single<TranslationRepository> {
        TranslationRepositoryImpl(
            dataStore = get(),
            database = get()
        )
    }

    /** Repositories implement interface [DatabaseListProvider] and its abstract class for implementation */
    single<DatabaseListProvider<Campaign>>(named(DINames.campaignsRepo)) {
        CampaignsProviderImpl(
            ioDispatcher = get(named(DINames.ioDispatcher)),
            getPagesApi = get(named(DINames.campaignsApi)),
            database = get()
        )
    }
    single<DatabaseListProvider<BonusCard>>(named(DINames.cardsRepo)) {
        BonusCardsProviderImpl(getListApi = get(named(DINames.cardsApi)))
    }
    single<DatabaseListProvider<City>>(named(DINames.citiesRepo)) {
        CitiesProviderImpl(getListApi = get(named(DINames.citiesApi)))
    }
    single<DatabaseListProvider<Club>>(named(DINames.clubsRepo)) {
        ClubsProviderImpl(getListApi = get(named(DINames.clubsApi)))
    }
    single<DatabaseListProvider<MiniGame>>(named(DINames.miniGamesRepo)) {
        MiniGamesProviderImpl(getListApi = get(named(DINames.miniGamesApi)))
    }
    single<DatabaseListProvider<ShopProperty>>(named(DINames.shopPropertiesRepo)) {
        ShopPropertiesProviderImpl(getListApi = get(named(DINames.shopPropertiesApi)))
    }
    single<DatabaseListProvider<QRLink>>(named(DINames.qrLinksRepo)) {
        QRLinksProviderImpl(getListApi = get(named(DINames.qrLinksApi)))
    }
    single<DatabaseListProvider<ShoppingIcons>>(named(DINames.shoppingIconsRepo)) {
        ShoppingIconsProviderImpl(getListApi = get(named(DINames.shoppingIconsApi)))
    }
    single<DatabaseListProvider<Shop>>(named(DINames.shopsRepo)) {
        ShopProviderImpl(
            ioDispatcher = get(named(DINames.ioDispatcher)),
            getListApi = get(named(DINames.shopsApi)),
            database = get()
        )
    }
    single<DatabaseListProvider<ShopType>>(named(DINames.shopTypesRepo)) {
        ShopTypesProviderImpl(getListApi = get(named(DINames.shopTypesApi)))
    }

    /** GET Repositories implement interface [ItemProvider] and its abstract class for implementation */
    single<ItemProvider<CampaignDataDto>>(named(DINames.campaignRepo)) { CampaignProviderImpl(itemApi = get(named(DINames.campaignApi))) }
    single<ItemProvider<Club>>(named(DINames.clubRepo)) { ClubProviderImpl(itemApi = get(named(DINames.clubApi))) }
    single<ItemProvider<DiscountsDataDto>>(named(DINames.discountsRepo)) { DiscountsDataProviderImpl(itemApi = get(named(DINames.discountsDataApi))) }
    single<ItemProvider<CountDto>>(named(DINames.messagesCountRepo)) { UnreadMessagesProviderImpl(itemApi = get(named(DINames.messagesCountApi))) }
    single<ItemProvider<CountDto>>(named(DINames.surveysCountRepo)) { SurveysCountProviderImpl(itemApi = get(named(DINames.surveysCountApi))) }
    single<ItemProvider<MonthProgressDto>>(named(DINames.monthProgressRepo)) { MonthProgressProviderImpl(itemApi = get(named(DINames.monthProgressApi))) }
    single<ItemProvider<List<Translation>>>(named(DINames.translationsRepo)) { TranslationsProviderImpl(itemApi = get(named(DINames.translationsApi))) }
    single<ItemProvider<PromoCodeDto>>(named(DINames.promoCodeProviderRepo)) { PromoCodeProviderImpl(itemApi = get(named(DINames.promoCodeProviderApi))) }
    single<ItemProvider<TransactionContent>>(named(DINames.transactionProviderRepo)) { TransactionProviderImpl(itemApi = get(named(DINames.transactionApi))) }
    single<ItemProvider<User>>(named(DINames.userRepo)) { ProfileProviderRepositoryImpl(api = get(named(DINames.profileProviderApi)), dataStore = get()) }

    /** DELETE Repositories implement interface [ItemProvider] and its abstract class for implementation */
    single<ItemProvider<Unit>>(named(DINames.deleteProfileRepo)) { DeleteProfileProviderImpl(api = get(named(DINames.deleteProfileApi))) }
    single<ItemProvider<Unit>>(named(DINames.exitClubRepo)) { ExitClubProviderImpl(api = get(named(DINames.exitClubApi))) }

    /** GET Repositories implement interface [ListProvider] and its abstract class for implementation */
    single<ListProvider<AboutDoc>>(named(DINames.aboutDocsListRepo)) { AboutDocsListProviderImpl(getListApi = get(named(DINames.aboutDocsApi))) }
    single<ListProvider<BonusCard>>(named(DINames.cardsListRepo)) { BonusCardsListProviderImpl(getListApi = get(named(DINames.cardsApi))) }
    single<ListProvider<Burning>>(named(DINames.burningListRepo)) { BurningsListProviderImpl(getListApi = get(named(DINames.burningsApi))) }
    single<ListProvider<Coupon>>(named(DINames.couponsListRepo)) { CouponsListProviderImpl(getListApi = get(named(DINames.couponsApi))) }
    single<ListProvider<Message>>(named(DINames.messagesRepo)) { MessagesListProviderImpl(getListApi = get(named(DINames.messagesApi))) }
    single<ListProvider<NavigationItem>>(named(DINames.mainButtonsListRepo)) {
        MainButtonsListProviderImpl(
            database = get(),
            couponsUseCase = get(named(DINames.couponsListUseCase)),
            surveysCountUseCase = get(named(DINames.surveysCountUseCase))
        )
    }
    single<ListProvider<QRLink>>(named(DINames.qrLinksRepo)) { QRLinksListProviderImpl(getListApi = get(named(DINames.qrLinksApi))) }
    single<ListProvider<Version>>(named(DINames.versionsRepo)) { VersionsListProviderImpl(getListApi = get(named(DINames.versionsApi))) }

    /** GET Repositories implement interface [PageProvider] and its abstract class for implementation */
    single<PageProvider<PartnerProductDto>>(named(DINames.partnersProductsRepo)) { PartnersProductsProviderImpl(getPageApi = get(named(DINames.partnersProductsApi))) }
    single<PageProvider<Transaction>>(named(DINames.transactionsRepo)) { TransactionsProviderImpl(getPageApi = get(named(DINames.transactionsApi))) }

    /** POST Repositories implement interface [PostProvider] and its abstract class for implementation */
    single<PostProvider<BonusCardBind, BonusCardDto>>(named(DINames.bindCardRepo)) { BonusCardBindProviderImp(postApi = get(named(DINames.bindCardApi))) }
    single<PostProvider<DeviceInfoDto, Unit>>(named(DINames.checkDeviceRepo)) { CheckDeviceProviderImp(postApi = get(named(DINames.deviceCheckApi))) }
    single<PostProvider<PhoneDto, Unit>>(named(DINames.checkPhoneRepo)) { CheckPhoneProviderImp(postApi = get(named(DINames.checkPhoneApi))) }
    single<PostProvider<Map<String, Int>, Unit>>(named(DINames.couponStateRepo)) { CouponStateProviderImp(postApi = get(named(DINames.couponStateApi))) }
    single<PostProvider<ProfileUpdateDto, ProfileDto>>(named(DINames.profileEditRepo)) { ProfileEditProviderImp(postApi = get(named(DINames.profileEditApi))) }
    single<PostProvider<Map<String, String>, Unit>>(named(DINames.enterClubRepo)) { EnterClubProviderImp(postApi = get(named(DINames.enterClubApi))) }
    single<PostProvider<Map<String, String>, Unit>>(named(DINames.iinRepo)) { IINProviderImp(postApi = get(named(DINames.iinApi))) }
    single<PostProvider<Map<String, String>, Unit>>(named(DINames.promoCodeActivateRepo)) { PromoCodeActivateProviderImp(postApi = get(named(DINames.promoCodeActivateApi))) }
    single<PostProvider<StickerExchangeDto, Unit>>(named(DINames.stickersExchangeRepo)) { StickerExchangeProviderImp(postApi = get(named(DINames.stickerExchangeApi))) }
}