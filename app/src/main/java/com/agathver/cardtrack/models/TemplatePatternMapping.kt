package com.agathver.cardtrack.models

import java.util.*

data class TemplatePatternMapping(
    val currency: (MatchResult) -> Currency,
    val amount: (MatchResult) -> Double,
    val cardType: (MatchResult) -> String,
    val cardIdentifier: (MatchResult) -> String,
    val merchant: (MatchResult) -> String
)
