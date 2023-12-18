package kz.magnum.app.di

import androidx.room.Room
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.data.dataStore.DataStoreRepositoryImpl
import aab.lib.commons.data.preferences.PreferenceRepository
import kz.magnum.app.data.room.MagnumClubDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStorageModule: Module = module {
    single { DataStoreRepositoryImpl(context = androidContext()) } bind DataStoreRepository::class
    single { PreferenceRepository(context = androidContext(), prefsName = "FlutterSharedPreferences") }
    single {
        Room.databaseBuilder(
            context = androidApplication(),
            klass = MagnumClubDatabase::class.java,
            name = "ClubDB"
        ).build()
    }
}