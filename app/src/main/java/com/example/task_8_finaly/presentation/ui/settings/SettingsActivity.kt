package com.example.task_8_finaly.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.task_8_finaly.R
import com.example.task_8_finaly.App
import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.creator.Creator
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsInteractor: SettingsInteractor
    private lateinit var themeSwitcher: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsInteractor = Creator.provideSettingsInteractor(this)

        themeSwitcher = findViewById(R.id.switchDark)


        themeSwitcher.isChecked = (applicationContext as App).darkTheme
        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            (applicationContext as App).switchTheme(checked)
            settingsInteractor.saveThemePreferences(checked)
        }

        //кнопочка назад должна воркинг

        val backButton: ImageButton = findViewById(R.id.buttonImage)
        backButton.setOnClickListener {
            finish()
        }

        val btnShare = findViewById<ImageView>(R.id.imageRepost)
        btnShare.setOnClickListener {
            val message = getString(R.string.yandex_curses)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(intent)
        }

        val btnSupport = findViewById<ImageView>(R.id.imageSupport)
        btnSupport.setOnClickListener {
            val subject = getString(R.string.title_email)
            val message = getString(R.string.text_email)
            val userMail = getString(R.string.email)
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(userMail))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(intent)
        }

        val btnTerms = findViewById<ImageView>(R.id.imageRight)
        btnTerms.setOnClickListener {
            val link = getString(R.string.offer)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
    }
}