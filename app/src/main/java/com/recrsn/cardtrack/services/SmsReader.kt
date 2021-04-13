package com.recrsn.cardtrack.services

import android.content.Context
import android.provider.Telephony
import com.recrsn.cardtrack.models.Result
import com.recrsn.cardtrack.models.Sms
import java.sql.Timestamp

class SmsReader(private val context: Context) {

    fun getAllSms(lastScan: Long, now: Long): Result<List<Sms>> {
        context.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.DATE_SENT,
                Telephony.Sms.Inbox.BODY
            ), Telephony.Sms.Inbox.DATE_SENT + " BETWEEN ? AND ?",
            arrayOf(lastScan.toString(), now.toString()),
            Telephony.Sms.Inbox.DATE_SENT + " DESC"
        ).use { cursor ->
            if (cursor == null) {
                return Result.error("Cannot open cursor")
            }

            cursor.moveToFirst()

            val smsList: MutableList<Sms> = mutableListOf()

            while (cursor.moveToNext()) {
                val sender =
                    cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.ADDRESS))
                val date =
                    Timestamp(cursor.getLong(cursor.getColumnIndex(Telephony.Sms.Inbox.DATE_SENT)))
                val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.Inbox.BODY))

                smsList.add(Sms(sender, date, body))
            }

            return Result.success(smsList)
        }
    }
}
