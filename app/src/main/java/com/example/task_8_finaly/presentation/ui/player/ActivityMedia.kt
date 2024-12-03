package com.example.task_8_finaly.presentation.ui.player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.task_8_finaly.databinding.ActivityMediaBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task_8_finaly.R

class ActivityMedia : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMediaBinding.inflate(layoutInflater)

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         */
    }
}