package com.agathver.cardtrack.repositories

import androidx.room.Dao
import androidx.room.Query
import com.agathver.cardtrack.models.Transaction

@Dao
interface TransactionRepository {

    @Query("SELECT * FROM `transaction` WHERE cardId = :cardId")
    fun getTransactionsForCard(cardId: Int): List<Transaction>
}
