package com.recrsn.cardtrack.services

import java.util.*

fun extractCurrency(group: Int): (MatchResult) -> Currency {
    return { result: MatchResult ->
        Currency.getInstance(result.groups[group]!!.value)
    }
}

fun currency(c: String): (MatchResult) -> Currency {
    return { _ -> Currency.getInstance(c) }
}

fun extractDouble(group: Int): (MatchResult) -> Double {
    return { result: MatchResult -> result.groups[group]!!.value.toDouble() }
}

fun extractString(group: Int): (MatchResult) -> String {
    return { result: MatchResult -> result.groups[group]!!.value }
}

fun string(s: String): (MatchResult) -> String {
    return { _ -> s }
}
