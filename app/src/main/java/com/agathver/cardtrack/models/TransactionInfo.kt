package com.agathver.cardtrack.models

import java.sql.Timestamp
import java.util.*

data class TransactionInfo(
    val amount: Double,
    val currency: Currency,
    val card: CardInfo,
    val merchant: String,
    val date: Timestamp
)
