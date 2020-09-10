package com.agathver.cardtrack.ui.cards

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agathver.cardtrack.db.CardTrackDatabase


class CardTransactionsViewModelFactory(application: Application, private val cardId: Int) :
    ViewModelProvider.Factory {
    private val db = CardTrackDatabase.getDatabase(application)
    private val transactionRepository = db.transactionRepository()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardTransactionsViewModel(cardId, transactionRepository) as T
    }

}
