package com.example.task_8_finaly.presentation.ui

import android.os.Handler
import android.os.Looper
import com.example.task_8_finaly.domain.api.UIHandler

class DefaultUIHandler : UIHandler {
    private val handler = Handler(Looper.getMainLooper())
    private var updateRunnable: Runnable? = null

    override fun startUpdating(onUpdate: () -> Unit) {
        updateRunnable = object : Runnable {
            override fun run() {
                onUpdate()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(updateRunnable!!)
    }

    override fun stopUpdating() {
        updateRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun resumeUpdating() {
        updateRunnable?.let { handler.post(it) }
    }
}