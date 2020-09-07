package com.agathver.cardtrack.models

import java.util.*

enum class CardType {
    CREDIT,
    DEBIT,
    UNKNOWN;

    companion object {
        fun fromString(cardType: String): CardType {
            return when (cardType.toUpperCase(Locale.ROOT)) {
                "CREDIT" -> CREDIT
                "DEBIT" -> DEBIT
                else -> UNKNOWN
            }
        }
    }
}
