package com.agathver.cardtrack.ui.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agathver.cardtrack.R
import com.agathver.cardtrack.models.Card
import java.util.*

class CardListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cards = emptyList<Card>() // Cached copy of words

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val primaryTextView: TextView = itemView.findViewById(R.id.item_text_primary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = inflater.inflate(R.layout.card, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.primaryTextView.text = holder.primaryTextView.resources.getString(
            R.string.card_item_title,
            card.bank,
            card.type.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT),
            card.identifier
        )
    }

    internal fun setCards(words: List<Card>) {
        this.cards = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = cards.size
}
