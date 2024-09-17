package com.example.task_8_finaly

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val themeSwitch: SwitchMaterial = findViewById(R.id.switchDark)
        val app = applicationContext as App

        themeSwitch.isChecked = app.darkTheme
        themeSwitch.setOnCheckedChangeListener { switcher, checked ->
            app.switchTheme(checked)

            //кнопочка назад должна воркинг

            val backButton: ImageButton = findViewById(R.id.buttonImage)
            backButton.setOnClickListener {
                finish()
            }
        }

        //BUTTON SHARE

        val shareImageButton: ImageView = findViewById(R.id.imageRepost)
        shareImageButton.setOnClickListener {
            val shareIntent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.yandex_curses))
                type = "text/plain"
            }

            val chooser = Intent.createChooser(shareIntent, null)
            startActivity(chooser)
        }

        //BUTTON SUPPORT

        val supportImageButton: ImageView = findViewById(R.id.imageSupport)
        supportImageButton.setOnClickListener{
            val supportIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_email))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_email))
            }
            val chooser = Intent.createChooser(supportIntent, null)
            startActivity(chooser)
        }

        //BUTTON DOCUMENTS

        val userAgreementButton: ImageView = findViewById(R.id.imageRight)
        userAgreementButton.setOnClickListener {
            val url = getString(R.string.offer)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}