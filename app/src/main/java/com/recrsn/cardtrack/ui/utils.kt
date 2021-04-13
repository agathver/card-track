package com.recrsn.cardtrack.ui

import com.recrsn.cardtrack.models.Transaction
import java.text.NumberFormat

fun formatCurrencyAmount(transaction: Transaction): String {
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.currency = transaction.currency
    return formatter.format(transaction.amount)
}
