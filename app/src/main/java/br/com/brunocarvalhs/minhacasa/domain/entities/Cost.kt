package br.com.brunocarvalhs.minhacasa.domain.entities

import android.os.Parcelable
import br.com.brunocarvalhs.minhacasa.domain.enums.Type
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Cost(
    @SerializedName(ID) val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) val name: String = EMPTY_STRING,
    @SerializedName(TYPE) val type: Type? = Type.Fix,
    @SerializedName(VALUE) val value: Double = ZERO_DOUBLE,
    @SerializedName(PAYMENT_VOUCHER) val paymentVoucher: String? = null,
    @SerializedName(TICKET) val ticket: String? = null,
    @SerializedName(IS_PAY) val isPay: Boolean = false,
) : Parcelable {

    fun toMap(): Map<String?, Any?> {
        val map: Map<String?, Any?> = HashMap()
        return Gson().fromJson(this.toJson(), map.javaClass)
    }

    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(value: String) = Gson().fromJson(value, Cost::class.java)

        const val COLLECTION = "costs"

        const val ID = "id"
        const val NAME = "name"
        const val TYPE = "type"
        const val VALUE = "value"
        const val PAYMENT_VOUCHER = "payment_voucher"
        const val TICKET = "ticket"
        const val IS_PAY = "is_pay"

        const val EMPTY_STRING = ""
        const val ZERO_DOUBLE = (0).toDouble()
    }
}
