package com.agathver.cardtrack.services

import android.content.SharedPreferences
import android.util.Log
import com.agathver.cardtrack.models.*
import com.agathver.cardtrack.repositories.CardRepository
import com.agathver.cardtrack.repositories.TransactionRepository

class TransactionImporter(
    private val sharedPreferences: SharedPreferences,
    private val smsReader: SmsReader,
    private val cardRepository: CardRepository,
    private val transactionRepository: TransactionRepository
) {
    companion object {
        private const val TAG = "TransactionImporter"
        const val LAST_TRANSACTION_IMPORT_KEY = "LAST_TRANSACTION_IMPORT"
    }

    fun importAllTransactions(): Result<Map<Card, List<Transaction>>> {
        val lastScan =
            sharedPreferences.getLong(LAST_TRANSACTION_IMPORT_KEY, System.currentTimeMillis() - 30 * 86400000L)
        val now = System.currentTimeMillis()

        Log.i(TAG, "Importing all transactions since $lastScan to $now")

        val result = smsReader.getAllSms(lastScan, now).map { smsList ->
            pairs(smsList.asSequence(), templates)
                .filter { (sms, template) -> template.senders.contains(sms.sender) }
                .map { (sms, template) -> template.tryParse(sms) }
                .filterNotNull()
                .groupBy { it.card }
                .mapKeys { (cardInfo, _) -> createCardIfNotExists(cardInfo) }
                .mapValues { (card, transactionInfoList) ->
                    createTransactionsFromTransactionInfo(card, transactionInfoList)
                }.toMap()
        }

        sharedPreferences.edit()
            .putLong(LAST_TRANSACTION_IMPORT_KEY, now)
            .apply()

        return result
    }

    private fun createTransactionsFromTransactionInfo(
        card: Card,
        transactions: List<TransactionInfo>
    ): List<Transaction> {
        return transactions.map { transactionInfo ->
            val transaction = Transaction(card, transactionInfo)
            Log.d(TAG, "Creating card_transaction $transaction")
            transactionRepository.insert(transaction)
            transaction
        }
    }

    private fun createCardIfNotExists(cardInfo: CardInfo): Card {
        var card = cardRepository.findByIdentity(
            cardInfo.identifier,
            cardInfo.type,
            cardInfo.bank,
            cardInfo.country
        )

        if (card == null) {
            card = Card(cardInfo.identifier, cardInfo.type, cardInfo.bank, cardInfo.country)
            Log.d(TAG, "Creating card $card")
            cardRepository.insert(card)
        }

        return card
    }

    private fun <X, Y> pairs(xList: Sequence<X>, yList: Sequence<Y>): Sequence<Pair<X, Y>> {
        return sequence {
            for (x: X in xList) {
                for (y: Y in yList) {
                    yield(Pair(x, y))
                }
            }
        }
    }
}
