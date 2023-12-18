package kz.magnum.app.di

import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.commonModels.QRLink
import kz.magnum.app.data.commonModels.ShoppingIcons
import kz.magnum.app.data.remote.HttpClientProvider
import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.accounting.BurningDto
import kz.magnum.app.data.remote.accounting.TransactionDto
import kz.magnum.app.data.remote.accounting.TransactionFullDataDto
import kz.magnum.app.data.remote.api.GetListApi
import kz.magnum.app.data.remote.api.GetPageApi
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.authentication.AuthenticationApi
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.data.remote.campaigns.CampaignDto
import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.data.remote.commons.ApiCaller
import kz.magnum.app.data.remote.commons.PageableDto
import kz.magnum.app.data.remote.coupons.CouponDataDto
import kz.magnum.app.data.remote.directory.CityDto
import kz.magnum.app.data.remote.directory.MiniGameDto
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.data.remote.directory.shops.ShopDto
import kz.magnum.app.data.remote.directory.shops.ShopPropertyDto
import kz.magnum.app.data.remote.directory.shops.ShopTypeDto
import kz.magnum.app.data.remote.directory.translations.TranslationsListDto
import kz.magnum.app.data.remote.discounts.DiscountsDataDto
import kz.magnum.app.data.remote.dto.MessageDto
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.data.remote.directory.AboutDocsDto
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.data.remote.profile.DeviceInfoDto
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.ProfileUpdateDto
import kz.magnum.app.data.remote.promotions.StickerExchangeDto
import kz.magnum.app.data.remote.stories.StoryDto
import kz.magnum.app.data.remote.versions.VersionDto
import kz.magnum.app.domain.apiImpl.AuthenticationApiImpl
import kz.magnum.app.domain.apiImpl.ItemApiImp
import kz.magnum.app.domain.apiImpl.deleteApis.DeleteProfile
import kz.magnum.app.domain.apiImpl.deleteApis.ExitClub
import kz.magnum.app.domain.apiImpl.getApis.GetPageApiImp
import kz.magnum.app.domain.apiImpl.getApis.items.CampaignItem
import kz.magnum.app.domain.apiImpl.getApis.items.ClubItem
import kz.magnum.app.domain.apiImpl.getApis.items.DiscountsData
import kz.magnum.app.domain.apiImpl.getApis.items.MonthProgress
import kz.magnum.app.domain.apiImpl.getApis.items.ProfileData
import kz.magnum.app.domain.apiImpl.getApis.items.PromoCode
import kz.magnum.app.domain.apiImpl.getApis.items.SurveysCount
import kz.magnum.app.domain.apiImpl.getApis.items.TranslationsData
import kz.magnum.app.domain.apiImpl.getApis.items.MessagesCount
import kz.magnum.app.domain.apiImpl.getApis.items.TransactionItem
import kz.magnum.app.domain.apiImpl.getApis.lists.GetAboutDocsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetBonusCardsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetBurningsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetCitiesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetClubsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetCouponsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetMessagesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetMiniGamesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetQRLInksList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetShopPropertiesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetShopTypesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetShoppingIconsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetShopsList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetStoriesList
import kz.magnum.app.domain.apiImpl.getApis.lists.GetVersionsList
import kz.magnum.app.domain.apiImpl.getApis.pages.GetCampaigns
import kz.magnum.app.domain.apiImpl.getApis.pages.GetPartnersProducts
import kz.magnum.app.domain.apiImpl.getApis.pages.GetTransactions
import kz.magnum.app.domain.apiImpl.postApis.EnterClubImpl
import kz.magnum.app.domain.apiImpl.postApis.PatchProfileImpl
import kz.magnum.app.domain.apiImpl.postApis.PostApiImpl
import kz.magnum.app.domain.apiImpl.postApis.PostBonusCardBindImpl
import kz.magnum.app.domain.apiImpl.postApis.PostCouponStateImpl
import kz.magnum.app.domain.apiImpl.postApis.PostDeviceCheckImpl
import kz.magnum.app.domain.apiImpl.postApis.PostIINImpl
import kz.magnum.app.domain.apiImpl.postApis.PostPromoCodeImpl
import kz.magnum.app.domain.apiImpl.postApis.PostStickerExchangeImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule: Module = module {

    single { HttpClientProvider(ioDispatcher = get(named(DINames.ioDispatcher)), dataStore = get(), refreshUseCase = get()) }
    single<AuthenticationApi> { AuthenticationApiImpl(httpClient = get(named(DINames.authClient)), dataStore = get()) }
    // Common APi-Caller to GET, POST, PATCH, and DELETE requests
    single { ApiCaller() }

    /** GET APIs implement interface [GetListApi] and its abstract class for implementation */
    single<GetListApi<BonusCardDto>>(named(DINames.cardsApi)) { GetBonusCardsList() }
    single<GetListApi<CityDto>>(named(DINames.citiesApi)) { GetCitiesList() }
    single<GetListApi<Club>>(named(DINames.clubsApi)) { GetClubsList() }
    single<GetListApi<AboutDocsDto>>(named(DINames.aboutDocsApi)) { GetAboutDocsList() }
    single<GetListApi<BurningDto>>(named(DINames.burningsApi)) { GetBurningsList() }
    single<GetListApi<CouponDataDto>>(named(DINames.couponsApi)) { GetCouponsList() }
    single<GetListApi<MessageDto>>(named(DINames.messagesApi)) { GetMessagesList() }
    single<GetListApi<MiniGameDto>>(named(DINames.miniGamesApi)) { GetMiniGamesList() }
    single<GetListApi<QRLink>>(named(DINames.qrLinksApi)) { GetQRLInksList() }
    single<GetListApi<ShopPropertyDto>>(named(DINames.shopPropertiesApi)) { GetShopPropertiesList() }
    single<GetListApi<ShopTypeDto>>(named(DINames.shopTypesApi)) { GetShopTypesList() }
    single<GetListApi<StoryDto>>(named(DINames.storiesApi)) { GetStoriesList() }
    single<GetListApi<ShopDto>>(named(DINames.shopsApi)) { GetShopsList() }
    single<GetListApi<ShoppingIcons>>(named(DINames.shoppingIconsApi)) { GetShoppingIconsList() }
    single<GetListApi<VersionDto>>(named(DINames.versionsApi)) { GetVersionsList() }

    /**
     * GET APIs implement interface [ItemApi] and its abstract class for implementation.
     * [ItemApiImp] requires generics <T> to GET as object.
     * */
    single<ItemApi<CampaignDataDto>>(named(DINames.campaignApi)) { CampaignItem() }
    single<ItemApi<Club>>(named(DINames.clubApi)) { ClubItem() }
    single<ItemApi<DiscountsDataDto>>(named(DINames.discountsDataApi)) { DiscountsData(dataStore = get()) }
    single<ItemApi<CountDto>>(named(DINames.messagesCountApi)) { MessagesCount() }
    single<ItemApi<CountDto>>(named(DINames.surveysCountApi)) { SurveysCount() }
    single<ItemApi<MonthProgressDto>>(named(DINames.monthProgressApi)) { MonthProgress() }
    single<ItemApi<PromoCodeDto>>(named(DINames.promoCodeProviderApi)) { PromoCode() }
    single<ItemApi<ProfileDto>>(named(DINames.profileProviderApi)) { ProfileData() }
    single<ItemApi<TransactionFullDataDto>>(named(DINames.transactionApi)) { TransactionItem() }
    single<ItemApi<TranslationsListDto>>(named(DINames.translationsApi)) { TranslationsData() }

    /**
     * DELETE APIs implement interface [ItemApi] and its abstract class for implementation of
     * [ItemApiImp] requires generics <T> to execute DELETE method.
     * */
    single<ItemApi<Unit>>(named(DINames.deleteProfileApi)) { DeleteProfile() }
    single<ItemApi<Unit>>(named(DINames.exitClubApi)) { ExitClub() }

    /**
     * GET APIs implement interface [GetPageApi] and its abstract class for implementation.
     * [GetPageApiImp] requires [PageableDto] as generics <T> to accept as successful answer.
     * */
    single<GetPageApi<CampaignDto>>(named(DINames.campaignsApi)) { GetCampaigns() }
    single<GetPageApi<PartnerProductDto>>(named(DINames.partnersProductsApi)) { GetPartnersProducts() }
    single<GetPageApi<TransactionDto>>(named(DINames.transactionsApi)) { GetTransactions() }

    /**
     * GET APIs implement interface [PostApi] and its abstract class for implementation.
     * [PostApiImpl] requires two generics: <T> as body, and <D> as successful answer.
     * */
    single<PostApi<BonusCardBind, BonusCardDto>>(named(DINames.bindCardApi)) { PostBonusCardBindImpl() }
    single<PostApi<Map<String, String>, Unit>>(named(DINames.enterClubApi)) { EnterClubImpl() }
    single<PostApi<DeviceInfoDto, Unit>>(named(DINames.deviceCheckApi)) { PostDeviceCheckImpl() }
    single<PostApi<Map<String, String>, Unit>>(named(DINames.iinApi)) { PostIINImpl() }
    single<PostApi<Map<String, Int>, Unit>>(named(DINames.couponStateApi)) { PostCouponStateImpl() }
    single<PostApi<Map<String, String>, Unit>>(named(DINames.promoCodeActivateApi)) { PostPromoCodeImpl() }
    single<PostApi<StickerExchangeDto, Unit>>(named(DINames.stickerExchangeApi)) { PostStickerExchangeImpl() }

    /**
     * PATCH APIs implement interface [PostApi] and its abstract class for implementation.
     * [PostApiImpl] requires two generics: <T> as body, and <D> as successful answer.
     * */
    single<PostApi<ProfileUpdateDto, ProfileDto>>(named(DINames.profileEditApi)) { PatchProfileImpl() }
}