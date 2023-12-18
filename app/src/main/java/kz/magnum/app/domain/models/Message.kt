package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseEntity

data class Message(
    override val id: Int,
    override val name: String,
    val dateCreated: String,
    val message: String,
    val androidLink: String?,
    val imageLink: String?,
    val button: String?,
): BaseEntity()
