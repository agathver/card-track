package com.recrsn.cardtrack.ui.cards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recrsn.cardtrack.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardsFragment : Fragment() {

    private val cardsViewModel: CardsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_cards, container, false)

        val cardList: RecyclerView = root.findViewById(R.id.card_list)
        val adapter = CardListAdapter(requireContext()) {
            val cardId = it.id

            val intent = Intent(requireContext(), CardTransactionsActivity::class.java)
            intent.putExtra(CardTransactionsActivity.ARG_CARD_ID, cardId)
            startActivity(intent)
        }
        cardList.adapter = adapter
        cardList.layoutManager = LinearLayoutManager(requireContext())

        cardsViewModel.cards.observe(viewLifecycleOwner, { cards ->
            cards?.let { adapter.setCards(it) }
        })

        cardsViewModel.loadAllTransactions()
        return root
    }
}
