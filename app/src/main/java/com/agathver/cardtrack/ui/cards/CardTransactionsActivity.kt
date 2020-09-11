package com.agathver.cardtrack.ui.cards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agathver.cardtrack.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CardTransactionsActivity : AppCompatActivity() {

    companion object {
        const val ARG_CARD_ID = "CARD_ID"
    }

    private val cardTransactionsViewModel: CardTransactionsViewModel by viewModel {
        parametersOf(this.intent.extras!!.getInt(ARG_CARD_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_transactions)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val transactionList: RecyclerView = findViewById(R.id.transaction_list)
        val adapter = CardTransactionListAdapter(this)
        transactionList.adapter = adapter
        transactionList.layoutManager = LinearLayoutManager(this)

        cardTransactionsViewModel.transactions.observe(this, {
            it?.let { adapter.setTransactions(it) }
        })
    }
}
