package com.agathver.cardtrack.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.agathver.cardtrack.models.Card
import com.agathver.cardtrack.models.CardType

@Dao
interface CardRepository {
    @Query("SELECT * FROM card ORDER BY identifier ASC")
    fun findAll(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE id = :id LIMIT 1")
    fun findById(id: Int): Card?

    @Insert
    fun insert(card: Card)

    @Query("SELECT * FROM card WHERE identifier = :identifier AND bank = :bank AND country = :country AND type = :type")
    fun findByIdentity(identifier: String, type: CardType, bank: String, country: String): Card?
}
