package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.accounting.BonusCardBind
import kz.magnum.app.data.remote.accounting.BonusCardDto
import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.repository.PostProviderImpl

class BonusCardBindProviderImp(postApi: PostApi<BonusCardBind, BonusCardDto>): PostProviderImpl<BonusCardBind, BonusCardDto>(postApi)
