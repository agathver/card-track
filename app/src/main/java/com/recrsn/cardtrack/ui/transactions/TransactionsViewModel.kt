package com.recrsn.cardtrack.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recrsn.cardtrack.repositories.TransactionRepository
import com.recrsn.cardtrack.services.TransactionImporter
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
