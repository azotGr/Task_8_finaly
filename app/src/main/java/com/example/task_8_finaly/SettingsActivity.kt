package com.example.task_8_finaly

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton: ImageButton = findViewById(R.id.buttonImage)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun share(view: View) {
        val send = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app))
            type = "text/plain"
        }

        val share = Intent.createChooser(send, null)
        startActivity(share)
    }

    fun writeSupport(view: View) {
        val email = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_email_1))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.support_email_2))
        }

        startActivity(Intent.createChooser(email, getString(R.string.share_title)))
    }
    fun openUsAgr(view: View) {
        val usAgr = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.user_agreement)))
        startActivity(usAgr)
    }
}