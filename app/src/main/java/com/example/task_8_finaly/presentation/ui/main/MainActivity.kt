package com.example.task_8_finaly.presentation.ui.main

import android.content.Intent
import android.os.Bundle
//import android.view.View
import android.widget.Button
import android.widget.Toolbar
//import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task_8_finaly.App
import com.example.task_8_finaly.R
import com.example.task_8_finaly.databinding.ActivityMainBinding
import com.example.task_8_finaly.presentation.Creator
import com.example.task_8_finaly.presentation.ui.player.ActivityMedia
import com.example.task_8_finaly.presentation.ui.search.SearchActivity
import com.example.task_8_finaly.presentation.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.buttonSetting.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }

        viewBinding.buttonSearch.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SearchActivity::class.java
                )
            )
        }

        viewBinding.buttonMedia.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ActivityMedia::class.java
                )
            )
        }
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
*/
        val settingsInteractor = Creator.getSettingInteractor()
        (applicationContext as App).switchTheme(settingsInteractor.getDarkTheme())
    }
}