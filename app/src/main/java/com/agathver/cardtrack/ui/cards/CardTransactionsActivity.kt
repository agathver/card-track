package com.agathver.cardtrack.ui.cards

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agathver.cardtrack.R

class CardTransactionsActivity : AppCompatActivity() {

    companion object {
        const val ARG_CARD_ID = "CARD_ID"
    }

    private val cardTransactionsViewModel: CardTransactionsViewModel by viewModels {
        CardTransactionsViewModelFactory(this.application, this.intent.extras!!.getInt(ARG_CARD_ID))
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
