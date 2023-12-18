package kz.magnum.app.data.repository.postProviderImpl

import kz.magnum.app.data.remote.api.PostApi
import kz.magnum.app.data.repository.PostProviderImpl

class EnterClubProviderImp(postApi: PostApi<Map<String,String>, Unit>): PostProviderImpl<Map<String, String>, Unit>(postApi)