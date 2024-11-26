package com.sukajee.cryptotracker.crypto.presentation.coin_detail

import android.icu.text.NumberFormat
import java.util.Locale


data class ValueLabel(
    val value: Float,
    val unit: String
) {
    fun formatted(): String {
        val formatter = NumberFormat
            .getNumberInstance(Locale.getDefault())
            .apply {
                val fractionDigits = when {
                    value >= 1000 -> 0
                    value in 10f..99f -> 2
                    value in 0f..10f -> 3
                    else -> 4
                }
                maximumFractionDigits = fractionDigits
                minimumFractionDigits = 0
            }

        return "${formatter.format(value)}$unit"
    }
}
