package br.com.brunocarvalhs.minhacasa.domain.enums

import androidx.annotation.StringRes
import br.com.brunocarvalhs.minhacasa.R
import com.google.gson.annotations.SerializedName

enum class Type(
    val id: Int,
    @StringRes val title: Int,
) {
    @SerializedName("Fix") Fix(id = 0, title = R.string.type_fix),
    @SerializedName("Variable") Variable(id = 1, title = R.string.type_var)
}