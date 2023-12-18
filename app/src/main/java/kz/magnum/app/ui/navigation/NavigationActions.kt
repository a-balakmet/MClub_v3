package kz.magnum.app.ui.navigation

import androidx.navigation.NavOptions
import aab.lib.commons.ui.navigation.NavigationAction

object NavigationActions {

    object Start {
        fun toGreetings() = object : NavigationAction {
            override val destination = Destinations.greetingsScreen
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }

        fun toPinCode() = object : NavigationAction {
            override val destination = Destinations.pinCodeScreen
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }
    }

    object Registration {

        fun toGeoNotification() = object : NavigationAction {
            override val destination = Destinations.geoNotification
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }
        fun toPhoneInput(restore: Boolean) = object : NavigationAction {
            override val destination = "${Destinations.phoneInput}/$restore"
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }

        fun toOtpInput(phone: String)  = object: NavigationAction {
            override val destination = "${Destinations.otpInput}/$phone"
        }

        fun toCardCreation() = object: NavigationAction {
            override val destination = Destinations.createCard
        }

        fun toCardScanner() = object: NavigationAction {
            override val destination = Destinations.cardScanner
        }

        fun toCardCreated() = object: NavigationAction {
            override val destination = Destinations.cardCreated
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }

        fun toProfileCreation() = object: NavigationAction {
            override val destination = Destinations.createProfile
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }
    }

    object Parents {

        fun toMain() = object : NavigationAction {
            override val destination = Destinations.mainScreen
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }
    }

    object SubScreen {

        fun toNavigatorParentScreen(screenName: String, bottomIndex: Int) =  object : NavigationAction {
            override val bottomIndex: Int = bottomIndex
            override val destination: String = "${Destinations.parentScreen}/$screenName"
            override val navOptions = NavOptions.Builder()
                .setPopUpTo(0, true)
                .setLaunchSingleTop(true)
                .build()
        }

        fun toParentScreen(screenName: String) = object : NavigationAction {
            override val destination: String = "${Destinations.parentScreen}/$screenName"
        }

        fun toCommonDetailScreen(childName: String) = object : NavigationAction {
            override val destination: String = "${Destinations.childScreen}/$childName"
        }

        fun toImageBarDetailScreen(childName: String) = object : NavigationAction {
            override val destination: String = "${Destinations.imageBarScreen}/$childName"
        }
    }

    object Commons {

        fun toWholesaleInfo() = object: NavigationAction {
            override val destination = Destinations.wholesaleInfoScreen
        }

        fun toPinCodeCreation(showBackArrow: Int) = object: NavigationAction {
            override val destination = "${Destinations.pinCodeCreation}/$showBackArrow"
        }
    }
}