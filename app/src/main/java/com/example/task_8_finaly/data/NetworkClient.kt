package com.example.task_8_finaly.data

import com.example.task_8_finaly.data.dto.Response


interface NetworkClient {
    fun doRequest(dto: Any): Response
}