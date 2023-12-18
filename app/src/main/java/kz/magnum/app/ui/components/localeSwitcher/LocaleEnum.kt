package kz.magnum.app.ui.components.localeSwitcher

enum class LocaleEnum(val lang: String) {
    RU(lang = "Русский") {
        override fun setLocale() = "ru"
    },

    KZ(lang = "Қазақша") {
        override fun setLocale() = "kk"
    };

    abstract fun setLocale(): String
}