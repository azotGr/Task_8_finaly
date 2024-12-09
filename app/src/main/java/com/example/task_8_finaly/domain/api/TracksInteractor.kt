package com.example.task_8_finaly.domain.api

import com.example.task_8_finaly.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TrackConsumer)
    interface TrackConsumer {
        fun consume(foundTracks: List<Track>)
    }

    fun getSearchedTracks(): ArrayList<Track>

    fun saveSearchedTracks(tracks: ArrayList<Track>)

    fun addTrackToHistory(track: Track)

    fun clearHistory()
}