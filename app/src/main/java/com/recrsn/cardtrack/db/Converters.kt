package com.recrsn.cardtrack.db

import androidx.room.TypeConverter
import com.recrsn.cardtrack.models.CardType
import java.sql.Timestamp
import java.util.*

class Converters {
    @TypeConverter
    fun fromCardType(c: CardType): String {
        return c.name
    }

    @TypeConverter
    fun toCardType(value: String): CardType {
        return CardType.valueOf(value)
    }

    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return currency.currencyCode
    }

    @TypeConverter
    fun toCurrency(currencyCode: String): Currency {
        return Currency.getInstance(currencyCode)
    }

    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp): Long {
        return timestamp.time
    }

    @TypeConverter
    fun toTimestamp(millis: Long): Timestamp {
        return Timestamp(millis)
    }
}
