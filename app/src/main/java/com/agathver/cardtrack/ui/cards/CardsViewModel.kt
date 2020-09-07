package com.agathver.cardtrack.ui.cards

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathver.cardtrack.db.CardTrackDatabase
import com.agathver.cardtrack.models.Card
import com.agathver.cardtrack.services.TransactionImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsViewModel(
    application: Application,
    private val cardImporter: TransactionImporter
) : ViewModel() {

    val cards: LiveData<List<Card>> =
        CardTrackDatabase.getDatabase(application).cardRepository().findAll()

    fun loadAllTransactions(ctx: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            cardImporter.importAllTransactions(ctx)
        }
    }
}
