package com.example.task_8_finaly.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.task_8_finaly.R
import com.example.task_8_finaly.App
import com.example.task_8_finaly.databinding.ActivitySettingsBinding
import com.example.task_8_finaly.domain.api.SettingsInteractor
import com.example.task_8_finaly.presentation.Creator

class SettingsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingsBinding
    private lateinit var settingsInteractor: SettingsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        settingsInteractor = Creator.getSettingInteractor()

        viewBinding.switchDark.isChecked = App.darkTheme
        viewBinding.switchDark.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            settingsInteractor.setDarkTheme(checked)
            settingsInteractor.setDarkTheme(viewBinding.switchDark.isChecked)
            App.darkTheme = settingsInteractor.getDarkTheme()
        }

        //кнопочка назад должна воркинг

        val backButton: ImageButton = findViewById(R.id.buttonImage)
        backButton.setOnClickListener {
            finish()
        }

        //BUTTON SHARE

        viewBinding.imageRepost.setOnClickListener {
            settingsInteractor.share(this)
        }

        //BUTTON SUPPORT

        viewBinding.imageSupport.setOnClickListener {
            settingsInteractor.help(this)
        }

        //BUTTON DOCUMENTS

        viewBinding.imageRight.setOnClickListener {
            val url = getString(R.string.offer)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        settingsInteractor.setDarkTheme(viewBinding.switchDark.isChecked)
        App.darkTheme = settingsInteractor.getDarkTheme()
    }
}