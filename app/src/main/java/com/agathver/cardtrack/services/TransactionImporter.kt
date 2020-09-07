package com.agathver.cardtrack.services

import android.content.Context
import com.agathver.cardtrack.models.*
import com.agathver.cardtrack.repositories.CardRepository

class TransactionImporter(
    private val smsReader: SmsReader,
    private val cardRepository: CardRepository
) {
    fun importAllTransactions(ctx: Context): Result<Map<Card, List<Transaction>>> {
        return smsReader.getAllSms(ctx).map { smsList ->
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
    }

    private fun createTransactionsFromTransactionInfo(
        card: Card,
        transactions: List<TransactionInfo>
    ): List<Transaction> {
        return transactions.map { transactionInfo -> Transaction(card, transactionInfo) }
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
