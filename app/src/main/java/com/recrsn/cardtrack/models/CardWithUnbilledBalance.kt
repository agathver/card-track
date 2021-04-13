package com.recrsn.cardtrack.models

data class CardWithUnbilledBalance(
    val id: Int,
    val identifier: String,
    val type: CardType,
    val bank: String,
    val country: String,
    val unbilledAmount: Double
)
