package com.example.task_8_finaly.domain.api

import com.example.task_8_finaly.domain.models.Track

interface TrackRepository {
    fun searchTracks(query: String, callback: (Result<List<Track>>) -> Unit)
}