package com.example.task_8_finaly.domain.impl

import com.example.task_8_finaly.domain.api.TrackRepository
import com.example.task_8_finaly.domain.api.TracksInteractor
import com.example.task_8_finaly.domain.models.Track

class TracksInteractorImpl(private val repository: TrackRepository) : TracksInteractor {

    override fun searchTracks(expression: String, consumer: TracksInteractor.TrackConsumer) {
        val t = Thread {
            consumer.consume(repository.searchTracks(expression))
        }

            t.start()
        }

    override fun getSearchedTracks(): ArrayList<Track> {
        return repository.getSearchedTracks()
    }

    override fun saveSearchedTracks(tracks: ArrayList<Track>) {
        repository.saveSearchedTracks(tracks)
    }

    override fun addTrackToHistory(track: Track) {
        repository.addTrackToHistory(track)
    }

    override fun clearHistory() {
        repository.clearHistory()
    }
}