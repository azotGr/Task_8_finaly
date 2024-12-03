package com.example.task_8_finaly.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.task_8_finaly.R

class SettingsManager(private val activityContext: Context) {
    val sharedPreferences = activityContext.getSharedPreferences("MODE", Context.MODE_PRIVATE)

    fun share(activityContext: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        val shareBody = activityContext.getString(R.string.yandex_curses)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareBody)
        activityContext.startActivity(Intent.createChooser(intent, "Share"))
    }

    fun help(activityContext: Context) {
        val message = activityContext.getString(R.string.text_email)
        val subject = activityContext.getString(R.string.title_email)
        val shareIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(activityContext.getString(R.string.email)))
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        activityContext.startActivity(shareIntent)
    }
}