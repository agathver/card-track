package com.recrsn.cardtrack.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.recrsn.cardtrack.models.Transaction
import com.recrsn.cardtrack.models.TransactionWithCardDetails

@Dao
interface TransactionRepository {
    @Query("SELECT * FROM `transaction` JOIN card ON `transaction`.cardId = card.id ORDER BY date DESC")
    fun findAll(): LiveData<List<TransactionWithCardDetails>>

    @Query("SELECT * FROM `transaction` WHERE cardId = :cardId ORDER BY date DESC")
    fun findByCard(cardId: Int): LiveData<List<Transaction>>

    @Query("SELECT sum(amount) FROM `transaction` WHERE cardId = :cardId")
    fun getAmountSpent(cardId: Int): Double

    @Insert
    fun insert(transaction: Transaction)
}
