package com.agathver.cardtrack.models

import java.sql.Timestamp

data class Sms(val sender: String, var date: Timestamp, val body: String)
