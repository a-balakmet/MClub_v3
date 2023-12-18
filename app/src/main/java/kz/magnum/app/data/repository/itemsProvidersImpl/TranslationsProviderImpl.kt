package kz.magnum.app.data.repository.itemsProvidersImpl

import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.directory.translations.TranslationsListDto
import kz.magnum.app.data.remote.directory.translations.toTranslationList
import kz.magnum.app.data.repository.ItemProviderImpl
import kz.magnum.app.data.room.entities.Translation

class TranslationsProviderImpl(itemApi: ItemApi<TranslationsListDto>) : ItemProviderImpl<TranslationsListDto, List<Translation>>(itemApi) {
    override val mapper = TranslationsListDto::toTranslationList
}