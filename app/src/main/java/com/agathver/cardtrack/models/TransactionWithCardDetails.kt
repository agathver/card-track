package com.agathver.cardtrack.models

import androidx.room.Embedded

data class TransactionWithCardDetails(
    @Embedded val transaction: Transaction,
    @Embedded val card: CardInfo,
) {
}
