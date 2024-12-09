package com.example.task_8_finaly.domain.api

interface UIHandler {
    fun startUpdating(onUpdate: () -> Unit)
    fun stopUpdating()
    fun resumeUpdating()
}