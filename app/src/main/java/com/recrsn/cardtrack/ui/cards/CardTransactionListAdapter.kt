package com.recrsn.cardtrack.ui.cards

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recrsn.cardtrack.R
import com.recrsn.cardtrack.models.Transaction
import com.recrsn.cardtrack.ui.formatCurrencyAmount

class CardTransactionListAdapter(
    context: Context
) : RecyclerView.Adapter<CardTransactionListAdapter.TransactionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var transactions = emptyList<Transaction>()

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionTextView: TextView = itemView.findViewById(R.id.transaction_description_text)
        val timestampTextView: TextView = itemView.findViewById(R.id.transaction_timestamp_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = inflater.inflate(R.layout.card_transaction, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.descriptionTextView.text = holder.descriptionTextView.resources.getString(
            R.string.transaction_description,
            formatCurrencyAmount(transaction),
            transaction.merchant
        )
        holder.timestampTextView.text =
            DateUtils.getRelativeDateTimeString(
                holder.itemView.context,
                transaction.date.time,
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.DAY_IN_MILLIS,
                0
            )
    }

    internal fun setTransactions(transactions: List<Transaction>?) {
        this.transactions = transactions ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = transactions.size
}
