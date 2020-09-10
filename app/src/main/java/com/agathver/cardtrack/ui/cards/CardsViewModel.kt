package com.agathver.cardtrack.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathver.cardtrack.models.Card
import com.agathver.cardtrack.repositories.CardRepository
import com.agathver.cardtrack.services.TransactionImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsViewModel(
    private val transactionImporter: TransactionImporter,
    cardRepository: CardRepository
) : ViewModel() {

    val cards: LiveData<List<Card>> = cardRepository.findAll()

    fun loadAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            transactionImporter.importAllTransactions()
        }
    }
}
