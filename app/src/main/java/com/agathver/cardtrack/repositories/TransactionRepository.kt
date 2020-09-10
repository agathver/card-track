package com.agathver.cardtrack.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.agathver.cardtrack.models.Transaction
import com.agathver.cardtrack.models.TransactionWithCardDetails

@Dao
interface TransactionRepository {

    @Query("SELECT * FROM `transaction` JOIN card ON `transaction`.cardId = card.id")
    fun findAll(): LiveData<List<TransactionWithCardDetails>>

    @Query("SELECT * FROM `transaction` WHERE cardId = :cardId")
    fun findByCard(cardId: Int): LiveData<List<Transaction>>

    @Insert
    fun insert(transaction: Transaction)
}
