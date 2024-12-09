package com.example.task_8_finaly.data.repository

import com.example.task_8_finaly.data.NetworkClient
import com.example.task_8_finaly.data.dto.TrackSearchRequest
import com.example.task_8_finaly.data.dto.TrackSearchResponse
import com.example.task_8_finaly.data.preference.TrackManager
import com.example.task_8_finaly.domain.api.TrackRepository
import com.example.task_8_finaly.domain.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient, private val trackManager: TrackManager) : TrackRepository {
    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as TrackSearchResponse).results.map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl)
            }
        } else {
            return emptyList()
        }
    }

    override fun getSearchedTracks(): ArrayList<Track> {
        return trackManager.readTracksFromSearchHistory()
    }

    override fun saveSearchedTracks(tracks: ArrayList<Track>) {
        trackManager.saveToSearchHistory(tracks)
    }

    override fun addTrackToHistory(track: Track) {
        trackManager.addTrackToHistory(track)
    }

    override fun clearHistory() {
        trackManager.clearHistory()
    }
}