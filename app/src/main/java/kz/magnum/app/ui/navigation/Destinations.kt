package kz.magnum.app.ui.navigation

object Destinations {
    // on start
    const val startScreen = "start_screen"
    const val greetingsScreen = "greetings_screen"
    const val pinCodeScreen = "pin_code_screen"

    // registration
    const val geoNotification = "geo_notification_screen"
    const val phoneInput = "phone_screen"
    const val otpInput = "otp_screen"
    const val createCard = "card_creation_screen"
    const val cardScanner = "card_scanner_screen"
    const val cardCreated = "card_created_screen"
    const val createProfile = "profile_creation_screen"

    // commons
    const val wholesaleInfoScreen = "wholesale_info_screen"
    const val pinCodeCreation = "pin_code_creation_screen"

    // parents
    const val mainScreen = "main_screen"
    const val discountsScreen = "discounts_screen"
    const val priceCheckScreen = "price_check_screen"
    const val campaignsScreen = "promotions_screen"
    const val settingsScreen = "settings_screen"
    const val transactionsScreen = "transactions_screen"
    const val purchasesScreen = "purchases_screen"

    // children
    const val childScreen = "child_screen"
    const val parentScreen = "parent_screen"
    const val imageBarScreen = "image_bar_screen"
    const val couponsScreen = "coupons_screen"

    /** children to inflate [childScreen] */
    const val messagesScreen = "notifications_title"
    const val monthProgressScreen = "main_rules"
    const val campaignScreen = "campaign_screen"
    const val clubScreen = "club_screen"
    const val promoCodesScreen = "promo_codes_screen"
    const val surveysScreen = "surveys_screen"
    const val shopsScreen = "shops_screen"
    const val miniGamesScreen = "mini_games_screen"
    const val aboutScreen = "about_program_short"
    const val couponScreen = "coupon_screen"
    const val transactionScreen = "transaction_screen"
}