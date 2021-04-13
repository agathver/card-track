package com.recrsn.cardtrack.models

import androidx.room.Embedded

data class TransactionWithCardDetails(
    @Embedded val transaction: Transaction,
    @Embedded val card: CardInfo,
) {
}
