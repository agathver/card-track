package com.agathver.cardtrack

import android.app.Application
import android.content.Context
import com.agathver.cardtrack.db.CardTrackDatabase
import com.agathver.cardtrack.services.SmsReader
import com.agathver.cardtrack.services.TransactionImporter
import com.agathver.cardtrack.ui.cards.CardTransactionsViewModel
import com.agathver.cardtrack.ui.cards.CardsViewModel
import com.agathver.cardtrack.ui.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(application: Application): Module {
    val db = CardTrackDatabase.getDatabase(application)

    return module {
        single {
            SmsReader(application)
        }
        single {
            TransactionImporter(
                transactionImporterSharedPreferences(application),
                get(),
                db.cardRepository(),
                db.transactionRepository()
            )
        }
        viewModel {
            CardsViewModel(get(), db.cardRepository(), db.transactionRepository())
        }
        viewModel {
            TransactionsViewModel(get(), db.transactionRepository())
        }
        viewModel { (cardId: Int) ->
            CardTransactionsViewModel(cardId, db.transactionRepository())
        }
    }
}

private fun transactionImporterSharedPreferences(application: Application) =
    application.getSharedPreferences(TransactionImporter::class.qualifiedName, Context.MODE_PRIVATE)
