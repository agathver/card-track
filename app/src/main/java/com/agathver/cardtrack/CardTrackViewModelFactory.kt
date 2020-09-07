package com.agathver.cardtrack

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agathver.cardtrack.db.CardTrackDatabase
import com.agathver.cardtrack.services.SmsReader
import com.agathver.cardtrack.services.TransactionImporter
import com.agathver.cardtrack.ui.cards.CardsViewModel

class CardTrackViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val db = CardTrackDatabase.getDatabase(application)
        val smsReader = SmsReader()
        val cardRepository = db.cardRepository()
        val cardImporter = TransactionImporter(smsReader, cardRepository)
        @Suppress("UNCHECKED_CAST")
        return CardsViewModel(application, cardImporter) as T
    }
}
