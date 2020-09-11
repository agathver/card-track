package com.agathver.cardtrack.ui

import com.agathver.cardtrack.models.Transaction
import java.text.NumberFormat

fun formatCurrencyAmount(transaction: Transaction): String {
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.currency = transaction.currency
    return formatter.format(transaction.amount)
}
