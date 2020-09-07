package com.agathver.cardtrack.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agathver.cardtrack.CardTrackViewModelFactory
import com.agathver.cardtrack.R

class CardsFragment : Fragment() {

    private lateinit var cardsViewModel: CardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModelFactory = CardTrackViewModelFactory(requireActivity().application)

        cardsViewModel = ViewModelProvider(this, viewModelFactory).get(CardsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_cards, container, false)

        val cardList: RecyclerView = root.findViewById(R.id.card_list)
        val adapter = CardListAdapter(requireContext())
        cardList.adapter = adapter
        cardList.layoutManager = LinearLayoutManager(requireContext())

        cardsViewModel.cards.observe(viewLifecycleOwner, { cards ->
            cards?.let { adapter.setCards(it) }
        })

        cardsViewModel.loadAllTransactions(this.requireContext())
        return root
    }
}
