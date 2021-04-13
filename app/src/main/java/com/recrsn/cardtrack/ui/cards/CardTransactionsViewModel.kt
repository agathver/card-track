package com.recrsn.cardtrack.ui.cards

import androidx.lifecycle.ViewModel
import com.recrsn.cardtrack.repositories.TransactionRepository


class CardTransactionsViewModel(
    cardId: Int,
    transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions = transactionRepository.findByCard(cardId)
}
