package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.remote.promotions.StickerExchangeDto
import kz.magnum.app.data.repository.PostProviderImpl

class StickerExchangeProviderImp(postApi: PostApi<StickerExchangeDto, Unit>): PostProviderImpl<StickerExchangeDto, Unit>(postApi)