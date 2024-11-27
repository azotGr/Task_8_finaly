package com.example.task_8_finaly.domain.impl

import com.example.task_8_finaly.domain.api.SearchTrackInter
import com.example.task_8_finaly.domain.api.TrackRepository
import com.example.task_8_finaly.domain.models.Track

class SearchTrackImpl(
    private val repository: TrackRepository
) : SearchTrackInter {
    override fun searchTracks(query: String, callback: (Result<List<Track>>) -> Unit) {
        repository.searchTracks(query) { result ->
            if (result.isSuccess) {
                callback(
                    Result.success(
                        result.getOrNull() ?: emptyList()
                    )
                ) // Возвращаем пустой список, если данных нет
            } else {
                callback(Result.failure(Exception()))
            }
        }
    }
}