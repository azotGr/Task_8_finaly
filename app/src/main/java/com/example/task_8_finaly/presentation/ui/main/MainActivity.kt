package com.example.task_8_finaly.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.task_8_finaly.R
import com.example.task_8_finaly.presentation.ui.player.ActivityMedia
import com.example.task_8_finaly.presentation.ui.search.SearchActivity
import com.example.task_8_finaly.presentation.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<Button>(R.id.buttonSetting)
        settingsButton.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }

        val searchButton= findViewById<Button>(R.id.buttonSearch)
        searchButton.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }

        val mediaButton = findViewById<Button>(R.id.buttonMedia)
        mediaButton.setOnClickListener {
            val displayIntent = Intent(this, ActivityMedia::class.java)
            startActivity(displayIntent)
        }
    }
}