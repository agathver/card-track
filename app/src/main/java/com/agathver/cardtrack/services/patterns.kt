package com.agathver.cardtrack.services

import com.agathver.cardtrack.models.Matcher
import com.agathver.cardtrack.models.SmsTemplate
import com.agathver.cardtrack.models.TemplatePatternMapping


val templates = sequenceOf(
    SmsTemplate(
        bank = "HDFC",
        country = "IN",
        senders = setOf(
            "JD-HDFCBK",
            "VK-HDFCBK",
            "VM-HDFCBK",
            "BP-HDFCBK",
            "VD-HDFCBK",
            "TM-HDFCBK",
            "QP-HDFCBK"
        ),
        matchers = listOf(
            Matcher(
                pattern = Regex("""ALERT:\s*You've spent\s+\w+\.(\d+\.\d{2})\s+(?:on|via) (\w+) Card (\w+) at (.+) on.+"""),
                mapping = TemplatePatternMapping(
                    currency = currency("INR"),
                    amount = extractDouble(1),
                    cardType = extractString(2),
                    cardIdentifier = extractString(3),
                    merchant = extractString(4)
                )
            ),
            Matcher(
                pattern = Regex("""ALERT:\s*\w+\.(\d+\.\d{2}) spent via (\w) Card (\w+) at (.+) on.+"""),
                mapping = TemplatePatternMapping(
                    currency = currency("INR"),
                    amount = extractDouble(1),
                    cardType = extractString(2),
                    cardIdentifier = extractString(3),
                    merchant = extractString(4)
                )
            )
        ),
    )
)
