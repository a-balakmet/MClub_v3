package kz.magnum.app.ui.components.localeSwitcher

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.extensions.toDataClass
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.room.MagnumClubDatabase
import kz.magnum.app.data.room.entities.Translation

class LocaleViewModel(
    val database: MagnumClubDatabase,
    val ioDispatcher: CoroutineScope,
    private val dataStore: DataStoreRepository
): ViewModel() {

    var appLocale = mutableStateOf("ru")
    var isShowMenu = mutableStateOf(false)
    private var fileTranslations: ArrayList<Translation> = ArrayList()
    private var dbTranslations: ArrayList<Translation> = ArrayList()

    init {
        getFileTranslations()
        viewModelScope.launch {
            appLocale.value = dataStore.readPreference(key = AppStoreKeys.locale, defaultValue = "ru")
        }
        ioDispatcher.launch {
            database.translationDao().emitTranslations().collect {
                dbTranslations.addAll(it)
            }
        }
    }

    fun setLocale(value: String) {
        isShowMenu.value = false
        appLocale.value = value
        viewModelScope.launch {
            dataStore.putPreference(AppStoreKeys.locale, value)
        }
    }

    fun switchMenu() {
        isShowMenu.value = !isShowMenu.value
    }

    private fun getFileTranslations() {
        val context = dataStore.provideContext()
        val jsonString = context.assets.open("langs.json")
            .bufferedReader()
            .use { it.readText() }

        jsonString.toDataClass<List<Translation>>()?.let {
            fileTranslations.addAll(it)
        }
    }

    fun getTranslation(key: String) : String {
        val dbTranslation = dbTranslations.firstOrNull { it.name == key }
        dbTranslation?.let {
            return when (appLocale.value) {
                "ru" -> it.ru
                "kk" -> it.kk
                else -> "unknown locale"
            }
        } ?: run {
            return getFileTranslation(key)
        }
    }

    private fun getFileTranslation(key: String): String {
        val fileTranslation = fileTranslations.firstOrNull { inFile -> inFile.name == key }
        return if (fileTranslation != null) {
            when (appLocale.value) {
                "ru" -> fileTranslation.ru
                "kk" -> fileTranslation.kk
                else -> "unknown locale"
            }
        } else {
            "unknown translation: $key"
        }
    }
}