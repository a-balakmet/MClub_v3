package kz.magnum.app.di

import kz.magnum.app.ui.activity.MainActivityViewModel
import kz.magnum.app.ui.builders.baseViewModels.DatabaseListViewModel
import kz.magnum.app.ui.builders.baseViewModels.ItemViewModel
import kz.magnum.app.ui.builders.baseViewModels.ListViewModel
import kz.magnum.app.ui.builders.baseViewModels.PagedListViewModel
import kz.magnum.app.ui.components.aboutDocs.AboutDocsViewModel
import kz.magnum.app.ui.components.bonusCards.BonusCardsViewModel
import kz.magnum.app.ui.components.buttonsGrid.ButtonsViewModel
import kz.magnum.app.ui.components.campaigns.item.CampaignViewModel
import kz.magnum.app.ui.components.campaigns.list.CampaignsViewModel
import kz.magnum.app.ui.components.clubs.item.ClubViewModel
import kz.magnum.app.ui.components.clubs.list.ClubsViewModel
import kz.magnum.app.ui.components.coupons.item.CouponViewModel
import kz.magnum.app.ui.components.coupons.list.CouponsListViewModel
import kz.magnum.app.ui.components.localeSwitcher.LocaleViewModel
import kz.magnum.app.ui.components.messages.list.MessagesListViewModel
import kz.magnum.app.ui.components.messages.unreadCount.MessagesCountViewModel
import kz.magnum.app.ui.components.miniGames.MiniGamesViewModel
import kz.magnum.app.ui.components.monthProgress.MonthProgressViewModel
import kz.magnum.app.ui.components.productsDialog.PartnerProductViewModel
import kz.magnum.app.ui.components.promoCode.PromoCodeViewModel
import kz.magnum.app.ui.components.transactions.item.TransactionViewModel
import kz.magnum.app.ui.components.transactions.lists.BurningsListViewModel
import kz.magnum.app.ui.components.transactions.lists.TransactionsViewModel
import kz.magnum.app.ui.screens.appBlock.main.MainScreenViewModel
import kz.magnum.app.ui.screens.commons.createPinCode.PinCodeCreationViewModel
import kz.magnum.app.ui.screens.commons.optInfo.WholesaleViewModel
import kz.magnum.app.ui.screens.loginBlock.cardScanner.CardScannerViewModel
import kz.magnum.app.ui.screens.loginBlock.createCard.CardCreationViewModel
import kz.magnum.app.ui.screens.loginBlock.createProfile.ProfileCreationViewModel
import kz.magnum.app.ui.screens.loginBlock.createdCard.CardCreatedViewModel
import kz.magnum.app.ui.screens.loginBlock.otpInput.OtpInputViewModel
import kz.magnum.app.ui.screens.loginBlock.phoneInput.PhoneInputViewModel
import kz.magnum.app.ui.screens.pinCode.PinCodeViewModel
import kz.magnum.app.ui.screens.start.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {
    // activity
    viewModel {
        MainActivityViewModel(
            handle = get(named(DINames.stateHandler)),
            dataStore = get(),
            prefs = get(),
            versionsUpdater = get(),
        )
    }

    // start
    viewModel {
        StartScreenViewModel(
            dataStore = get(),
            database = get(),
            navigator = get()
        )
    }
    viewModel {
        PinCodeViewModel(
            ioDispatcher = get(named(DINames.ioDispatcher)),
            navigator = get(),
            dataStore = get(),
            translationUseCase = get()
        )
    }

    // registration
    viewModel {
        LocaleViewModel(
            database = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            dataStore = get()
        )
    }
    viewModel {
        PhoneInputViewModel(
            useCase = get(),
            navigator = get()
        )
    }
    viewModel {
        OtpInputViewModel(
            navigator = get(),
            checkPhoneUseCase = get(),
            registrationUseCase = get(),
            cardsProvider = get(named(DINames.cardsListRepo)),
            profileProviderUseCase = get(named(DINames.userUseCase)),
            deviceInfoUseCase = get(),
            database = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            dataStore = get(),
        )
    }
    viewModel {
        CardCreationViewModel(
            navigator = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            database = get(),
            dataStore = get(),
            useCase = get()
        )
    }
    viewModel {
        CardScannerViewModel(
            navigator = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            database = get(),
            dataStore = get(),
        )
    }
    viewModel {
        CardCreatedViewModel(
            database = get(),
            navigator = get()
        )
    }
    viewModel {
        ProfileCreationViewModel(
            navigator = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            dataStore = get(),
            translationUseCase = get(),
            useCase = get()
        )
    }

    // parents
    viewModel {
        MainScreenViewModel(
            handle = get(named(DINames.stateHandler)),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            navigator = get(),
            translationUseCase = get(),
            database = get(),
            dataStore = get(),
            deleteUseCase = get(named(DINames.deleteProfileUseCase)),
            profileProviderUseCase = get(named(DINames.userUseCase)),
        )
    }

    /** View models for single views from [ListViewModel] */
    viewModel {
        AboutDocsViewModel(useCase = get(named(DINames.aboutDocsListUseCase)))
    }
    viewModel {
        ButtonsViewModel(useCase = get(named(DINames.mainButtonsListUseCase)))
    }
    viewModel {
        BurningsListViewModel(useCase = get(named(DINames.burningsListUseCase)))
    }
    viewModel {
        CouponsListViewModel(useCase = get(named(DINames.couponsListUseCase)))
    }
    viewModel {
        MessagesListViewModel(useCase = get(named(DINames.messagesUseCase)))
    }

    // common
    viewModel {
        WholesaleViewModel(
            navigator = get(),
            ioDispatcher = get(named(DINames.ioDispatcher)),
            database = get(),
            dataStore = get(),
            iinUseCase = get(),
            useCase = get(),
        )
    }
    viewModel {
        PinCodeCreationViewModel(
            navigator = get(),
            dataStore = get()
        )
    }

    // in-App standalone
    viewModel { CouponViewModel(ioDispatcher = get(named(DINames.ioDispatcher)), couponStateUseCase = get(), dataStore = get()) }

    /** View models for single views from [DatabaseListViewModel] */
    viewModel {
        BonusCardsViewModel(
            handle = get(named(DINames.stateHandler)),
            useCase = get(named(DINames.cardsUseCase))
        )
    }
    viewModel {
        CampaignsViewModel(
            handle = get(named(DINames.stateHandler)),
            useCase = get(named(DINames.campaignsUseCase))
        )
    }
    viewModel {
        ClubsViewModel(
            handle = get(named(DINames.stateHandler)),
            useCase = get(named(DINames.clubsUseCase))
        )
    }
    viewModel {
        MiniGamesViewModel(
            handle = get(named(DINames.stateHandler)),
            useCase = get(named(DINames.miniGamesUseCase))
        )
    }

    /** View models for single views from [ItemViewModel] */
    viewModel {
        MessagesCountViewModel(useCase = get(named(DINames.messagesCountUseCase)))
    }
    viewModel {
        MonthProgressViewModel(useCase = get(named(DINames.monthProgressUseCase)))
    }
    viewModel {
        CampaignViewModel(
            useCase = get(named(DINames.campaignUseCase)),
            stickersExchangeUseCase = get()
        )
    }
    viewModel {
        ClubViewModel(
            useCase = get(named(DINames.clubUseCase)),
            exitClubUseCase = get(named(DINames.exitClubUseCase)),
            enterClubUseCase = get(),
            database = get()
        )
    }
    viewModel {
        PromoCodeViewModel(
            database = get(),
            useCase = get(named(DINames.promoCodeProviderUseCase)),
            activateUseCase = get()
        )
    }
    viewModel {
        TransactionViewModel(useCase = get(named(DINames.transactionProviderUseCase)))
    }

    /** View models for paged views from [PagedListViewModel] */
    viewModel {
        PartnerProductViewModel(useCase = get(named(DINames.partnerProductsUseCase)))
    }
    viewModel {
        TransactionsViewModel(useCase = get(named(DINames.transactionsUseCase)))
    }
}