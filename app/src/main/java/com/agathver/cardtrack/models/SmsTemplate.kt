package com.agathver.cardtrack.models

data class SmsTemplate(
    val senders: Set<String>,
    val matchers: List<Matcher>,
    val bank: String,
    val country: String,
) {

    fun tryParse(sms: Sms): TransactionInfo? {
        for (matcher in matchers) {
            val matchResult = matcher.pattern.matchEntire(sms.body) ?: continue

            val currency = matcher.mapping.currency(matchResult)
            val amount = matcher.mapping.amount(matchResult)
            val merchant = matcher.mapping.merchant(matchResult)

            val cardIdentifier = matcher.mapping.cardIdentifier(matchResult)
            val cardType = CardType.fromString(matcher.mapping.cardType(matchResult))

            val card = CardInfo(cardIdentifier, cardType, bank, country)

            return TransactionInfo(amount, currency, card, merchant, sms.date)
        }

        return null
    }
}
