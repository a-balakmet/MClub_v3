package kz.magnum.app

import android.app.Application
import kz.magnum.app.di.dataStorageModule
import kz.magnum.app.di.ktorClientsModule
import kz.magnum.app.di.navigationModule
import kz.magnum.app.di.networkModule
import kz.magnum.app.di.objectsModule
import kz.magnum.app.di.repositoriesModule
import kz.magnum.app.di.useCasesModule
import kz.magnum.app.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            // inject Android context
            androidContext(this@App)
            // inject modules
            modules(
                listOf(
                    objectsModule,
                    navigationModule,
                    dataStorageModule,
                    viewModelsModule,
                    ktorClientsModule,
                    networkModule,
                    repositoriesModule,
                    useCasesModule,
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}