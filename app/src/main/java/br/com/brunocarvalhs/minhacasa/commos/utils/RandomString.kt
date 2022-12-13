package br.com.brunocarvalhs.minhacasa.commos.utils

import java.util.*

internal fun String.Companion.random(sizeOfRandomString: Int): String {
    val alphaNumericType = "0123456789qwertyuiopasdfghjklzxcvbnm"
    val random = Random()
    val sb = StringBuilder(sizeOfRandomString)
    for (i in 0 until sizeOfRandomString)
        sb.append(alphaNumericType[random.nextInt(alphaNumericType.length)])
    return sb.toString()
}