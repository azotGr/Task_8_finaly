package com.example.task_8_finaly.presentation.ui.player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.task_8_finaly.databinding.ActivityMediaBinding
import androidx.appcompat.app.AppCompatActivity

class ActivityMedia : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewBinding = ActivityMediaBinding.inflate(layoutInflater)
    }
}