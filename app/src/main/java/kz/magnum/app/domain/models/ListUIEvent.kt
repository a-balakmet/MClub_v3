package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.domain.models.QueryModel
import aab.lib.commons.ui.navigation.NavigationAction
import android.content.Context

sealed class ListUIEvent<out T> {
    data object LoadNext: ListUIEvent<Nothing>()
    data class Refresh(val queries: List<QueryModel>?): ListUIEvent<Nothing>()
    data class DropQueries(val defaultQueries: List<QueryModel>?): ListUIEvent<Nothing>()
    data class SelectAndNavigate<out T:BaseEntity> (val item: T, val childScreen: String, val bottomIndex: Int, val isNavigate: Boolean): ListUIEvent<T>()
    data class NavigateByActions<out T:BaseEntity> (val bottomIndex: Int, val screenName: String): ListUIEvent<T>()
    data class DirectNavigation(val action: NavigationAction): ListUIEvent<Nothing>()
    data class OpenLink(val context: Context, val link: String): ListUIEvent<Nothing>()
}
