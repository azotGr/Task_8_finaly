package com.example.task_8_finaly

import android.content.Intent
import android.os.Bundle
//import android.view.View
import android.widget.Button
//import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

/*
        val buttonSr = findViewById<Button>(R.id.buttonSearch)
        val buttonSrClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Найди меня", Toast.LENGTH_SHORT).show()
            }
        }

        buttonSr.setOnClickListener(buttonSrClickListener)

        val buttonMedia = findViewById<Button>(R.id.buttonMedia)

        buttonMedia.setOnClickListener {
            Toast.makeText(this@MainActivity, "Тунь-тунь", Toast.LENGTH_SHORT).show()
        }

        val buttonSetting = findViewById<Button>(R.id.buttonSetting)

        buttonSetting.setOnClickListener{
            Toast.makeText(this@MainActivity, "Подожди чу чут", Toast.LENGTH_SHORT).show()
        }
*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}