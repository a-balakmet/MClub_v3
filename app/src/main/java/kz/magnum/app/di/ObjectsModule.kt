package kz.magnum.app.di

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.magnum.app.di.DINames.ioDispatcher
import kz.magnum.app.di.DINames.stateHandler
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val objectsModule: Module = module {
    single(named(ioDispatcher)) { CoroutineScope(Dispatchers.IO) }
    single(named(stateHandler)) { SavedStateHandle }
}