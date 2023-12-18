package kz.magnum.app.di

import kz.magnum.app.data.commonModels.Club
import kz.magnum.app.data.remote.campaigns.CampaignDataDto
import kz.magnum.app.data.remote.campaigns.MonthProgressDto
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.data.remote.directory.PartnerProductDto
import kz.magnum.app.data.remote.dto.PromoCodeDto
import kz.magnum.app.data.room.entities.BonusCard
import kz.magnum.app.data.room.entities.Campaign
import kz.magnum.app.data.room.entities.MiniGame
import kz.magnum.app.domain.models.AboutDoc
import kz.magnum.app.domain.models.Burning
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.models.Message
import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.models.transactions.Transaction
import kz.magnum.app.domain.models.transactions.TransactionContent
import kz.magnum.app.domain.useCase.TranslationUseCase
import kz.magnum.app.domain.useCase.VersionsUpdaterUseCase
import kz.magnum.app.domain.useCase.authentication.CheckPhoneUseCase
import kz.magnum.app.domain.useCase.authentication.RefreshTokensUseCase
import kz.magnum.app.domain.useCase.authentication.RegistrationUseCase
import kz.magnum.app.domain.useCase.databaseListsUseCase.BonusCardsDatabaseListUseCase
import kz.magnum.app.domain.useCase.databaseListsUseCase.CampaignsDatabaseListUseCase
import kz.magnum.app.domain.useCase.databaseListsUseCase.ClubsDatabaseListUseCase
import kz.magnum.app.domain.useCase.databaseListsUseCase.DatabaseListUseCase
import kz.magnum.app.domain.useCase.databaseListsUseCase.MiniGamesDatabaseListUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.CampaignUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.ClubUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.DeleteProfileUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.ExitClubUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.MonthProgressUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.ProfileUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.PromoCodeUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.SurveysCountUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.TransactionUseCase
import kz.magnum.app.domain.useCase.itemsUseCase.UnreadMessagesUseCase
import kz.magnum.app.domain.useCase.listsUseCase.AboutDocsListUseCase
import kz.magnum.app.domain.useCase.listsUseCase.BurningsListUseCase
import kz.magnum.app.domain.useCase.listsUseCase.CouponsListUseCase
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.domain.useCase.listsUseCase.MainButtonsGreedUseCase
import kz.magnum.app.domain.useCase.listsUseCase.MessagesListUseCase
import kz.magnum.app.domain.useCase.pagesUseCase.PageUseCase
import kz.magnum.app.domain.useCase.pagesUseCase.PartnersProductsUseCase
import kz.magnum.app.domain.useCase.pagesUseCase.TransactionsUseCase
import kz.magnum.app.domain.useCase.postUseCase.BonusCardBindUseCase
import kz.magnum.app.domain.useCase.postUseCase.CheckDeviceUseCase
import kz.magnum.app.domain.useCase.postUseCase.CouponStateUseCase
import kz.magnum.app.domain.useCase.postUseCase.EnterClubUseCase
import kz.magnum.app.domain.useCase.postUseCase.IINUseCase
import kz.magnum.app.domain.useCase.postUseCase.PostUseCase
import kz.magnum.app.domain.useCase.postUseCase.ProfileEditUseCase
import kz.magnum.app.domain.useCase.postUseCase.PromoCodeActivateUseCase
import kz.magnum.app.domain.useCase.postUseCase.StickersExchangeUseCase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule: Module = module {

    /** Authentication */
    factory { CheckPhoneUseCase(api = get()) }
    factory { RegistrationUseCase(authApi = get()) }
    factory { RefreshTokensUseCase(api = get()) }

    /** GET UseCases from  [DatabaseListUseCase] */
    factory<DatabaseListUseCase<BonusCard>>(named(DINames.cardsUseCase)) { BonusCardsDatabaseListUseCase(repository = get(named(DINames.cardsRepo))) }
    factory<DatabaseListUseCase<Campaign>>(named(DINames.campaignsUseCase)) { CampaignsDatabaseListUseCase(repository = get(named(DINames.campaignsRepo))) }
    factory<DatabaseListUseCase<Club>>(named(DINames.clubsUseCase)) { ClubsDatabaseListUseCase(repository = get(named(DINames.clubsRepo))) }
    factory<DatabaseListUseCase<MiniGame>>(named(DINames.miniGamesUseCase)) { MiniGamesDatabaseListUseCase(repository = get(named(DINames.miniGamesRepo))) }

    /** GET UseCases from [ItemUseCase] */
    factory<ItemUseCase<CountDto>>(named(DINames.messagesCountUseCase)) { UnreadMessagesUseCase(repository = get(named(DINames.messagesCountRepo))) }
    factory<ItemUseCase<CountDto>>(named(DINames.surveysCountUseCase)) { SurveysCountUseCase(repository = get(named(DINames.messagesCountRepo))) }
    factory<ItemUseCase<MonthProgressDto>>(named(DINames.monthProgressUseCase)) { MonthProgressUseCase(repository = get(named(DINames.monthProgressRepo))) }
    factory<ItemUseCase<CampaignDataDto>>(named(DINames.campaignUseCase)) { CampaignUseCase(repository = get(named(DINames.campaignRepo))) }
    factory<ItemUseCase<Club>>(named(DINames.clubUseCase)) { ClubUseCase(repository = get(named(DINames.clubRepo))) }
    factory<ItemUseCase<User>>(named(DINames.userUseCase)) { ProfileUseCase(repository = get(named(DINames.userRepo))) }
    factory<ItemUseCase<PromoCodeDto>>(named(DINames.promoCodeProviderUseCase)) { PromoCodeUseCase(repository = get(named(DINames.promoCodeProviderRepo))) }
    factory<ItemUseCase<TransactionContent>>(named(DINames.transactionProviderUseCase)) { TransactionUseCase(repository = get(named(DINames.transactionProviderRepo))) }

    /** DELETE UseCases from [ItemUseCase] */
    factory<ItemUseCase<Unit>>(named(DINames.deleteProfileUseCase)) { DeleteProfileUseCase(repository = get(named(DINames.deleteProfileRepo))) }
    factory<ItemUseCase<Unit>>(named(DINames.exitClubUseCase)) { ExitClubUseCase(repository = get(named(DINames.exitClubRepo))) }

    /** GET UseCases from [ListUseCase] */
    factory<ListUseCase<AboutDoc>>(named(DINames.aboutDocsListUseCase)) { AboutDocsListUseCase(repository = get(named(DINames.aboutDocsListRepo))) }
    factory<ListUseCase<Burning>>(named(DINames.burningsListUseCase)) { BurningsListUseCase(repository = get(named(DINames.burningListRepo))) }
    factory<ListUseCase<Coupon>>(named(DINames.couponsListUseCase)) { CouponsListUseCase(repository = get(named(DINames.couponsListRepo))) }
    factory<ListUseCase<NavigationItem>>(named(DINames.mainButtonsListUseCase)) { MainButtonsGreedUseCase(repository = get(named(DINames.mainButtonsListRepo))) }
    factory<ListUseCase<Message>>(named(DINames.messagesUseCase)) { MessagesListUseCase(repository = get(named(DINames.messagesRepo))) }

    /** GET UseCases from [PageUseCase] */
    factory<PageUseCase<PartnerProductDto>>(named(DINames.partnerProductsUseCase)) { PartnersProductsUseCase(repository = get(named(DINames.partnersProductsRepo))) }
    factory<PageUseCase<Transaction>>(named(DINames.transactionsUseCase)) { TransactionsUseCase(repository = get(named(DINames.transactionsRepo))) }

    /** POST/PATCH UseCases that inherit [PostUseCase]*/
    factory { BonusCardBindUseCase(repository = get(named(DINames.bindCardRepo))) }
    factory { CheckDeviceUseCase(repository = get(named(DINames.checkDeviceRepo))) }
    factory { CouponStateUseCase(repository = get(named(DINames.couponStateRepo))) }
    factory { EnterClubUseCase(repository = get(named(DINames.enterClubRepo))) }
    factory { IINUseCase(repository = get(named(DINames.iinRepo))) }
    factory { ProfileEditUseCase(repository = get(named(DINames.profileEditRepo))) }
    factory { PromoCodeActivateUseCase(repository = get(named(DINames.promoCodeActivateRepo))) }
    factory { StickersExchangeUseCase(repository = get(named(DINames.stickersExchangeRepo))) }

    /** Standalone use cases */
    factory { TranslationUseCase(repository = get()) }
    factory {
        VersionsUpdaterUseCase(
            versionsSavingRepository = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            dataStore = get(),
            database = get()
        )
    }
}