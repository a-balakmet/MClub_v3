package kz.magnum.app.data.repository.itemsProvidersImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import aab.lib.commons.data.dataStore.DataStoreRepository
import aab.lib.commons.domain.models.Resource
import kz.magnum.app.data.AppStoreKeys
import kz.magnum.app.data.remote.api.ItemApi
import kz.magnum.app.data.remote.profile.ProfileDto
import kz.magnum.app.data.remote.profile.toUser
import kz.magnum.app.domain.models.profile.User
import kz.magnum.app.domain.repository.dynamics.ItemProvider

class ProfileProviderRepositoryImpl(
    private val api: ItemApi<ProfileDto>,
    private val dataStore: DataStoreRepository
) : ItemProvider<User> {

    override suspend fun fetch(id: Int?): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        when (val response = api.getItem(null)) {
            is Resource.Success -> {
                val user = response.data.toUser()
                val userData = Json.encodeToString(user)
                dataStore.putPreference(AppStoreKeys.phone, response.data.phone)
                dataStore.putPreference(AppStoreKeys.user, userData)
                emit(Resource.Success(data = user))
            }

            is Resource.Error -> emit(response)
            else -> Unit
        }
    }
}