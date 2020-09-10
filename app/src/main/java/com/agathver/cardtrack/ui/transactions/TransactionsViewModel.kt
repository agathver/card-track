package com.agathver.cardtrack.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agathver.cardtrack.repositories.TransactionRepository
import com.agathver.cardtrack.services.TransactionImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val transactionImporter: TransactionImporter,
    transactionRepository: TransactionRepository
) :
    ViewModel() {
    val transactions = transactionRepository.findAll()

    fun loadAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            transactionImporter.importAllTransactions()
        }
    }
}
