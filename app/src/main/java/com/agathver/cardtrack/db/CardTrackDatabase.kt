package com.agathver.cardtrack.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.agathver.cardtrack.models.Card
import com.agathver.cardtrack.models.Transaction
import com.agathver.cardtrack.repositories.CardRepository
import com.agathver.cardtrack.repositories.TransactionRepository

@Database(entities = [Card::class, Transaction::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CardTrackDatabase : RoomDatabase() {

    abstract fun cardRepository(): CardRepository

    abstract fun transactionRepository(): TransactionRepository

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CardTrackDatabase? = null

        fun getDatabase(context: Context): CardTrackDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardTrackDatabase::class.java,
                    "card_track"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
