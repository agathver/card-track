package com.agathver.cardtrack.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Card::class,
        parentColumns = ["id"],
        childColumns = ["cardId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Transaction(
    @ColumnInfo(index = true)
    val cardId: Int,
    val amount: Double,
    val currency: Currency,
    val merchant: String,
    val date: Timestamp
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(card: Card, transactionInfo: TransactionInfo) : this(
        card.id,
        transactionInfo.amount,
        transactionInfo.currency,
        transactionInfo.merchant,
        transactionInfo.date
    )
}
