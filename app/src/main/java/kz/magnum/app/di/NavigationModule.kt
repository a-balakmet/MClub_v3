package kz.magnum.app.di

import aab.lib.commons.ui.navigation.CustomNavigator
import aab.lib.commons.ui.navigation.Navigator
import kz.magnum.app.ui.navigation.NavigationViewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule: Module = module {
    single { CustomNavigator() } bind Navigator::class
    single { NavigationViewModel(navigator = get()) }
}