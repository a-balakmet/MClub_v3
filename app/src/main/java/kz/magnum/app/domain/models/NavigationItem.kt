package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseEntity
import aab.lib.commons.ui.navigation.NavigationAction

data class NavigationItem(
    override val id: Int,
    override val name: String,
    val translatableText: Boolean,
    val icon: Int,
    val imageLink: String?,
    val isIcon: Boolean,
    val additionalValue: Int?,
    val action: NavigationAction?,
    val externalLink: String?
): BaseEntity()
