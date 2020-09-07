package com.agathver.cardtrack.services

import android.content.Context
import android.provider.Telephony
import com.agathver.cardtrack.models.Result
import com.agathver.cardtrack.models.Sms
import java.sql.Timestamp

class SmsReader {

    fun getAllSms(ctx: Context): Result<List<Sms>> {
        ctx.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms.Inbox.ADDRESS,
                Telephony.Sms.Inbox.DATE_SENT,
                Telephony.Sms.Inbox.BODY
            ), "date >= ?", arrayOf((System.currentTimeMillis() - 30 * 86400000L).toString()),
            "date DESC"
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
