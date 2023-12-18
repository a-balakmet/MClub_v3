package kz.magnum.app.domain.apiImpl.getApis.items

import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.remote.directory.translations.TranslationsListDto
import kz.magnum.app.domain.apiImpl.ItemApiImp

class TranslationsData : ItemApiImp<TranslationsListDto>() {

    override val endPoint: String = "directory/content/translations/list"
    override suspend fun backEndItem(id: Int?): Resource<TranslationsListDto> = apiCaller.getRequest(httpClient, endPoint)

}