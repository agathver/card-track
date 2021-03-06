package com.recrsn.cardtrack.ui.cards

import androidx.lifecycle.*
import com.recrsn.cardtrack.models.CardWithUnbilledBalance
import com.recrsn.cardtrack.repositories.CardRepository
import com.recrsn.cardtrack.repositories.TransactionRepository
import com.recrsn.cardtrack.services.TransactionImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsViewModel(
    private val transactionImporter: TransactionImporter,
    cardRepository: CardRepository,
    transactionRepository: TransactionRepository
) : ViewModel() {

    val cards: LiveData<List<CardWithUnbilledBalance>> =
        Transformations.switchMap(cardRepository.findAll()) { listOfCards ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(listOfCards.map { card ->
                    val unbilledAmount = transactionRepository.getAmountSpent(card.id)
                    CardWithUnbilledBalance(
                        card.id,
                        card.identifier,
                        card.type,
                        card.bank,
                        card.country,
                        unbilledAmount
                    )
                })
            }
        }

    fun loadAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            transactionImporter.importAllTransactions()
        }
    }
}
