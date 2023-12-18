package kz.magnum.app.ui.navigation

import aab.lib.commons.ui.navigation.NavigationAction
import aab.lib.commons.ui.navigation.Navigator
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import kz.magnum.app.R
import kz.magnum.app.domain.models.NavigationItem

class NavigationViewModel(val navigator: Navigator) : ViewModel() {

    var bottomIndex = 0

    val phoneNavItems = listOf(
        NavigationItem(
            id = 0,
            name = "main_title",
            translatableText = true,
            icon = R.drawable.main,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.mainScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        ),
        NavigationItem(
            id = 1,
            name = "discounts",
            translatableText = true,
            icon = R.drawable.discount,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.discountsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        ),
        NavigationItem(
            id = 2,
            name = "get_price",
            translatableText = true,
            icon = R.drawable.price_check,
            imageLink = null,
            isIcon = false,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.priceCheckScreen
            },
            externalLink = null
        ),
        NavigationItem(
            id = 3,
            name = "promotions",
            translatableText = true,
            icon = R.drawable.promo,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.campaignsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        ),
        NavigationItem(
            id = 4,
            name = "settings",
            translatableText = true,
            isIcon = true,
            additionalValue = null,
            icon = R.drawable.settings,
            imageLink = null,
            action = object : NavigationAction {
                override val destination = Destinations.settingsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        )
    )

    val tabletNavItems = listOf(
        NavigationItem(
            id = 0,
            name = "main_title",
            translatableText = true,
            icon = R.drawable.main,
            imageLink = null,
            isIcon = true,
            action = object : NavigationAction {
                override val destination = Destinations.mainScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            additionalValue = null,
            externalLink = null
        ),
        NavigationItem(
            id = 5,
            name = "transactions_title",
            translatableText = true,
            icon = R.drawable.transactions,
            imageLink = null,
            isIcon = true,
            action = object : NavigationAction {
                override val destination = Destinations.transactionsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            additionalValue = null,
            externalLink = null
        ),
        NavigationItem(
            id = 6,
            name = "my_lists",
            translatableText = true,
            imageLink = null,
            isIcon = true,
            icon = R.drawable.purchases,
            action = object : NavigationAction {
                override val destination = Destinations.purchasesScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            additionalValue = null,
            externalLink = null
        ),
        NavigationItem(
            id = 2,
            name = "get_price",
            translatableText = true,
            icon = R.drawable.price_check,
            imageLink = null,
            isIcon = false,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.priceCheckScreen
            },
            externalLink = null
        ),
        NavigationItem(
            id = 1,
            name = "discounts",
            translatableText = true,
            icon = R.drawable.discount,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.discountsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        ),
        NavigationItem(
            id = 3,
            name = "promotions",
            translatableText = true,
            icon = R.drawable.promo,
            imageLink = null,
            isIcon = true,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.campaignsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        ),
        NavigationItem(
            id = 4,
            name = "settings",
            translatableText = true,
            icon = R.drawable.settings,
            isIcon = true,
            imageLink = null,
            additionalValue = null,
            action = object : NavigationAction {
                override val destination = Destinations.settingsScreen
                override val navOptions = NavOptions.Builder()
                    .setPopUpTo(0, true)
                    .setLaunchSingleTop(true)
                    .build()
            },
            externalLink = null
        )
    )

    fun navigateByParents(destination: NavigationItem) {
        if (destination.name != "get_price") bottomIndex = destination.id
        when (destination.name) {
            "get_price", "main_title" -> navigator.navigate(destination.action, bottomIndex)
            else -> {
                navigator.navigate(NavigationActions.SubScreen.toNavigatorParentScreen(destination.name, destination.id), destination.id)
//                bottomIndex = destination.id
//                navigator.navigate(destination.action, destination.id)
            }

        }
    }

    fun navigateByIndex() {
        bottomIndex = 0
        val parentScreen = tabletNavItems[0]
        navigator.navigate(parentScreen.action, parentScreen.id)
    }

    fun goBack() {
        navigator.getController()?.popBackStack()
    }
}