package com.recrsn.cardtrack.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index("identifier", "country", "bank", "type")])
data class Card(
    val identifier: String,
    val type: CardType,
    val bank: String,
    val country: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
