package kz.magnum.app.ui.navigation

import aab.lib.commons.ui.navigation.Navigator
import aab.lib.commons.ui.navigation.asLifecycleAwareState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kz.magnum.app.domain.models.Coupon
import kz.magnum.app.ui.screens.appBlock.discounts.DiscountsScreen
import kz.magnum.app.ui.screens.appBlock.main.MainScreen
import kz.magnum.app.ui.screens.appBlock.priceChecker.PriceCheckerScreen
import kz.magnum.app.ui.screens.appBlock.purchases.PurchasesScreen
import kz.magnum.app.ui.screens.appBlock.settings.SettingsScreen
import kz.magnum.app.ui.screens.appBlock.singleScreens.CommonDetailScreen
import kz.magnum.app.ui.screens.appBlock.singleScreens.ImageAppBarDetailsScreen
import kz.magnum.app.ui.screens.appBlock.singleScreens.ParentScreen
import kz.magnum.app.ui.screens.commons.createPinCode.PinCodeCreationScreen
import kz.magnum.app.ui.screens.commons.optInfo.WholesaleInfoScreen
import kz.magnum.app.ui.screens.loginBlock.cardScanner.CardScannerScreen
import kz.magnum.app.ui.screens.loginBlock.createCard.CardCreationScreen
import kz.magnum.app.ui.screens.loginBlock.createProfile.ProfileCreationScreen
import kz.magnum.app.ui.screens.loginBlock.createdCard.CardCreatedScreen
import kz.magnum.app.ui.screens.loginBlock.geoNotification.GeoNotificationScreen
import kz.magnum.app.ui.screens.loginBlock.greetings.GreetingsScreen
import kz.magnum.app.ui.screens.loginBlock.otpInput.OtpInputScreen
import kz.magnum.app.ui.screens.loginBlock.phoneInput.PhoneInputScreen
import kz.magnum.app.ui.screens.pinCode.PinCodeScreen
import kz.magnum.app.ui.screens.start.StartScreen
import org.koin.compose.koinInject

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    navigator: Navigator = koinInject(),
) {

    navigator.setController(controller = navController)
    val lifecycleOwner = LocalLifecycleOwner.current
    val navigatorState by navigator.navigationActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    LaunchedEffect(navigatorState) {
        navigatorState?.let {
            it.parcelableArguments.forEach { arg ->
                navController.currentBackStackEntry?.arguments?.putParcelable(arg.key, arg.value)
            }
            navController.navigate(route = it.destination, navOptions = it.navOptions)
        }
    }

    NavHost(navController, startDestination = Destinations.startScreen) {
        // start
        composable(route = Destinations.startScreen) { StartScreen() }
        composable(route = Destinations.greetingsScreen) { GreetingsScreen() }
        composable(route = Destinations.pinCodeScreen) { PinCodeScreen() }
        // registration
        composable(route = Destinations.geoNotification) { GeoNotificationScreen() }
        composable(
            route = "${Destinations.phoneInput}/{restore}",
            arguments = listOf(navArgument("restore") { type = NavType.BoolType })
        ) {
            val restore = it.arguments?.getBoolean("restore")
            restore?.let {
                PhoneInputScreen(restore = restore)
            }
        }
        composable(
            route = "${Destinations.otpInput}/{phone}",
            arguments = listOf(navArgument("phone") { type = NavType.StringType })
        ) {
            val phone = it.arguments?.getString("phone")
            phone?.let { thePhone ->
                OtpInputScreen(phone = thePhone, onBackPressed = { navController.popBackStack() })
            }
        }
        composable(route = Destinations.createCard) { CardCreationScreen(onBackPressed = { navController.popBackStack() }) }
        composable(route = Destinations.cardScanner) { CardScannerScreen(onBackPressed = { navController.popBackStack() }) }
        composable(route = Destinations.cardCreated) { CardCreatedScreen(onBackPressed = { navController.popBackStack() }) }
        composable(route = Destinations.createProfile) { ProfileCreationScreen(onBackPressed = { navController.popBackStack() }) }
        // application - parent screens
        composable(route = Destinations.mainScreen) { MainScreen() }
        composable(route = Destinations.discountsScreen) { DiscountsScreen() }
//        composable(route = Destinations.campaignsScreen) { CampaignsScreen() }
        composable(route = Destinations.priceCheckScreen) { PriceCheckerScreen() }
        composable(route = Destinations.settingsScreen) { SettingsScreen() }
//        composable(route = Destinations.transactionsScreen) { TransactionsScreen() }
        composable(route = Destinations.purchasesScreen) { PurchasesScreen() }

        // application
        // - parents for two panes
        composable(
            route = "${Destinations.parentScreen}/{screenName}",
            arguments = listOf(navArgument("screenName") { type = NavType.StringType })
        ) {
            val title = it.arguments?.getString("screenName")
            title?.let {
                ParentScreen(title = title, onBackPressed = { navController.popBackStack() })
            }
        }
        // - children
        composable(
            route = "${Destinations.childScreen}/{childName}",
            arguments = listOf(navArgument("childName") { type = NavType.StringType })
        ) {
            val title = it.arguments?.getString("childName")
            val coupon: Coupon? = navController.previousBackStackEntry?.savedStateHandle?.get("coupon")
            title?.let {
                CommonDetailScreen(title = title, coupon = coupon, onBackPressed = { navController.popBackStack() })
            }
        }
        composable(
            route = "${Destinations.imageBarScreen}/{childName}",
            arguments = listOf(navArgument("childName") { type = NavType.StringType })
        ) {
            val type = it.arguments?.getString("childName")
            type?.let {
                ImageAppBarDetailsScreen(type = type, onBackPressed = { navController.popBackStack() })
            }
        }

        // commons
        composable(route = Destinations.wholesaleInfoScreen) { WholesaleInfoScreen(onBackPressed = { navController.popBackStack() }) }
        composable(
            route = "${Destinations.pinCodeCreation}/{showBackArrow}",
            arguments = listOf(navArgument("showBackArrow") { type = NavType.IntType })
        ) {
            val showBackArrow = it.arguments?.getInt("showBackArrow")
            showBackArrow?.let {
                PinCodeCreationScreen(showBackArrow = showBackArrow, onBackPressed = { navController.popBackStack() })
            }
        }
    }
}