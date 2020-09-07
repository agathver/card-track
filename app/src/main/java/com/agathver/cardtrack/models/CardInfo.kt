package com.agathver.cardtrack.models

data class CardInfo(
    val identifier: String,
    val type: CardType,
    val bank: String,
    val country: String,
)
