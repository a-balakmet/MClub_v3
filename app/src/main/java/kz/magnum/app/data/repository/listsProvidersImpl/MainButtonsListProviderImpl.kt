package kz.magnum.app.data.repository.listsProvidersImpl

import aab.lib.commons.domain.models.Resource
import aab.lib.commons.ui.navigation.NavigationAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.magnum.app.R
import kz.magnum.app.data.remote.commons.CountDto
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.domain.models.NavigationItem
import kz.magnum.app.domain.repository.dynamics.ListProvider
import kz.magnum.app.domain.useCase.itemsUseCase.ItemUseCase
import kz.magnum.app.domain.useCase.listsUseCase.ListUseCase
import kz.magnum.app.ui.navigation.Destinations

class MainButtonsListProviderImpl(
    private val database: MagnumClubDatabase,
    private val couponsUseCase: ListUseCase<Coupon>,
    private val surveysCountUseCase: ItemUseCase<CountDto>,
) : ListProvider<NavigationItem> {

    override suspend fun fetch(): Flow<Resource<List<NavigationItem>>> = flow {
        emit(Resource.Loading)
        val buttons: ArrayList<NavigationItem> = ArrayList()
        couponsUseCase.invoke().collect {
            when (it) {
                is Resource.Loading -> emit(Resource.Loading)
                is Resource.Success -> {
                    val availableCoupons = it.data.filter { c -> c.stateInt <= 2 }
                    if (availableCoupons.isNotEmpty()) {
                        buttons.add(
                            NavigationItem(
                                id = 107,
                                name = "coupons",
                                translatableText = true,
                                icon = R.drawable.coupons,
                                imageLink = null,
                                isIcon = true,
                                additionalValue = availableCoupons.size,
                                action = object : NavigationAction {
                                    override val destination = Destinations.couponsScreen
                                },
                                externalLink = null
                            )
                        )
                    } else buttons.add(couponsButton)
                }

                else -> buttons.add(couponsButton)
            }
        }
        surveysCountUseCase.invoke(null).collect {
            when (it) {
                is Resource.Success -> {
                    if (it.data.value > 0) {
                        buttons.removeIf { b-> b.id == 110 }
                        buttons.add(
                            NavigationItem(
                                id = 110,
                                name = "surveys",
                                translatableText = true,
                                icon = R.drawable.survey,
                                imageLink = null,
                                additionalValue = it.data.value,
                                isIcon = true,
                                action = object : NavigationAction {
                                    override val destination = Destinations.surveysScreen
                                },
                                externalLink = null
                            )
                        )
                    }
                }

                else -> buttons.add(surveysButton)
            }
        }
        buttons.add(priceCheckButton)
        buttons.add(magnumButton)
        buttons.add(transactionsButton)
        buttons.add(purchasesButton)
        buttons.add(favShopButton)
        buttons.add(promoCodesButton)
        buttons.add(clubsButton)
        buttons.add(shopsButton)
        buttons.add(miniGamesButton)
        buttons.add(callButton)
        buttons.add(aboutButton)
        emit(Resource.Success(buttons.sortedBy { it.id }))
        database.qrLinksDao().emitLinks().collect { qrLinks ->
            for (i in qrLinks.indices) {
                val button = NavigationItem(
                    id = i + 1,
                    name = qrLinks[i].name,
                    translatableText = false,
                    icon = R.drawable.magnum,
                    imageLink = qrLinks[i].image,
                    isIcon = false,
                    additionalValue = null,
                    action = null,
                    externalLink = qrLinks[i].link
                )
                buttons.add(button)
            }
            emit(Resource.Success(buttons.sortedBy { it.id }))
        }
    }

    companion object {
        val priceCheckButton = NavigationItem(
            id = 0,
            name = "get_price",
            translatableText = true,
            imageLink = null,
            isIcon = false,
            additionalValue = null,
            icon = R.drawable.price_check,
            action = object : NavigationAction {
                override val destination = Destinations.priceCheckScreen
            },
            externalLink = null
        )
        val magnumButton = NavigationItem(
            id = 103,
            name = "Magnum.kz",
            translatableText = false,
            icon = R.drawable.magnum,
            imageLink = null,
            isIcon = false,
            additionalValue = null,
            action = null,
            externalLink = "https://magnum.kz/"
        )
        val transactionsButton = NavigationItem(
            id = 104,
            name = "transactions_title",
            translatableText = true,
            icon = R.drawable.transactions,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.transactionsScreen
            },
            externalLink = null
        )
        val purchasesButton = NavigationItem(
            id = 105,
            name = "my_lists",
            translatableText = true,
            icon = R.drawable.purchases,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.purchasesScreen
            },
            externalLink = null
        )
        val favShopButton = NavigationItem(
            id = 106,
            name = "syiykty",
            translatableText = true,
            icon = R.drawable.heart_outlined,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = null,
            externalLink = null
        )
        val couponsButton = NavigationItem(
            id = 107,
            name = "coupons",
            translatableText = true,
            icon = R.drawable.coupons,
            imageLink = null,
            isIcon = true,
            additionalValue = -1,
            action = object : NavigationAction {
                override val destination = Destinations.couponsScreen
            },
            externalLink = null
        )
        val promoCodesButton = NavigationItem(
            id = 108,
            name = "promocodes",
            translatableText = true,
            icon = R.drawable.promo_code,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.promoCodesScreen
            },
            externalLink = null
        )
        val clubsButton = NavigationItem(
            id = 109,
            name = "clubs_title",
            translatableText = true,
            icon = R.drawable.clubs,
            imageLink = null,
            additionalValue = null,
            isIcon = true,
            action = object : NavigationAction {
                override val destination = Destinations.clubScreen
            },
            externalLink = null
        )
        val surveysButton = NavigationItem(
            id = 110,
            name = "surveys",
            translatableText = true,
            icon = R.drawable.survey,
            imageLink = null,
            additionalValue = -1,
            isIcon = true,
            action = object : NavigationAction {
                override val destination = Destinations.surveysScreen
            },
            externalLink = null
        )
        val shopsButton = NavigationItem(
            id = 111,
            name = "my_shops",
            translatableText = true,
            icon = R.drawable.shop_pin,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.shopsScreen
            },
            externalLink = null
        )
        val miniGamesButton = NavigationItem(
            id = 112,
            name = "Mini Games",
            translatableText = false,
            isIcon = true,
            additionalValue = null,
            icon = R.drawable.mini_games,
            imageLink = null,
            action = object : NavigationAction {
                override val destination = Destinations.miniGamesScreen
            },
            externalLink = null
        )
        val callButton = NavigationItem(
            id = 113,
            name = "support",
            translatableText = true,
            icon = R.drawable.phone,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = null,
            externalLink = "tel:7766"
        )
        val aboutButton = NavigationItem(
            id = 114,
            name = "about_program_short",
            translatableText = true,
            icon = R.drawable.about,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.aboutScreen
            },
            externalLink = null
        )

    }
}