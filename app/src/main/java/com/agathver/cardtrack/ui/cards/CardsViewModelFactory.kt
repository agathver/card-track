package com.agathver.cardtrack.ui.cards

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agathver.cardtrack.db.CardTrackDatabase
import com.agathver.cardtrack.services.SmsReader
import com.agathver.cardtrack.services.TransactionImporter
import com.agathver.cardtrack.ui.transactions.TransactionsViewModel

class CardsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    private val db = CardTrackDatabase.getDatabase(application)
    private val smsReader = SmsReader(application)
    private val cardRepository = db.cardRepository()
    private val transactionRepository = db.transactionRepository()
    private val cardImporter = TransactionImporter(
        transactionImporterSharedPreferences(),
        smsReader,
        cardRepository,
        transactionRepository
    )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass.canonicalName) {
            CardsViewModel::class.qualifiedName -> {
                @Suppress("UNCHECKED_CAST")
                return CardsViewModel(cardImporter, cardRepository) as T
            }
            TransactionsViewModel::class.qualifiedName -> {
                return TransactionsViewModel(cardImporter, transactionRepository) as T
            }
        }
        throw UnsupportedOperationException("Dont know how to instantiate ${modelClass.canonicalName}")
    }

    private fun transactionImporterSharedPreferences() =
        application.getSharedPreferences(TransactionImporter::class.qualifiedName, MODE_PRIVATE)
}
