package com.agathver.cardtrack.ui.cards

import androidx.lifecycle.ViewModel
import com.agathver.cardtrack.repositories.TransactionRepository


class CardTransactionsViewModel(
    cardId: Int,
    transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions = transactionRepository.findByCard(cardId)
}
