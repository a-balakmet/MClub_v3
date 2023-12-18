package kz.magnum.app.domain.models

import aab.lib.commons.data.room.BaseEntity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Coupon(
    override val id: Int,
    override val name: String,
    val couponId: Int,
    val subTitle: String,
    val type: String,
    val description: String,
    val instructions: String,
    val value: String,
    val imageLink: String,
    val stateInt: Int,
    val stateString: String,
    val couponColor: Int,
    val dateFrom: LocalDateTime,
    val dateTo: LocalDateTime,
    val withProducts: Boolean
): BaseEntity(), Parcelable