package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseEntity

data class AboutDoc(override val id: Int, override val name: String, val link: String): BaseEntity()
