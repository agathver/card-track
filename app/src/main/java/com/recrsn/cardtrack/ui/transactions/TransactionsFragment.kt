package com.recrsn.cardtrack.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recrsn.cardtrack.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionsFragment : Fragment() {

    private val transactionsViewModel: TransactionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_transactions, container, false)

        val transactionList: RecyclerView = root.findViewById(R.id.all_transactions_list)
        val adapter = TransactionListAdapter(requireContext())
        transactionList.adapter = adapter
        transactionList.layoutManager = LinearLayoutManager(requireContext())

        transactionsViewModel.transactions.observe(viewLifecycleOwner, {
            it?.let { adapter.setTransactions(it) }
        })

        transactionsViewModel.loadAllTransactions()
        return root
    }
}
